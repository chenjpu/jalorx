package io.jalorx.registd.config;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.AppEnv;
import io.jalorx.boot.JalorVersion;
import io.micronaut.context.annotation.BootstrapContextCompatible;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.env.EnvironmentPropertySource;
import io.micronaut.context.env.PropertySource;
import io.micronaut.context.env.PropertySourceLoader;
import io.micronaut.context.env.yaml.YamlPropertySourceLoader;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.core.util.StringUtils;
import io.micronaut.discovery.config.ConfigurationClient;
import io.micronaut.discovery.consul.ConsulConfiguration;
import io.micronaut.discovery.consul.client.v1.ConsulClient;
import io.micronaut.discovery.consul.client.v1.KeyValue;
import io.micronaut.discovery.consul.condition.RequiresConsul;
import io.micronaut.discovery.consul.config.ConsulConfigurationClient;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.scheduling.TaskExecutors;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

@Singleton
@RequiresConsul
@Requires(beans = ConsulClient.class)
@Requires(property = ConfigurationClient.ENABLED, value = StringUtils.TRUE, defaultValue = StringUtils.FALSE)
@Replaces(ConsulConfigurationClient.class)
@BootstrapContextCompatible
public class RegistdConfigurationClient implements ConfigurationClient {
  private static final Logger LOG = LoggerFactory.getLogger(RegistdConfigurationClient.class);

  private String prefix = "registd";

  private final ConsulClient consulClient;
  private final AppEnv appEnv;
  private final ConsulConfiguration consulConfiguration;
  private final Map<String, PropertySourceLoader> loaderByFormatMap = new ConcurrentHashMap<>();
  private ExecutorService executionService;

  /**
   * @param consulClient The consul client
   * @param consulConfiguration The consul configuration
   * @param environment The environment
   */
  public RegistdConfigurationClient(ConsulClient consulClient, AppEnv appEnv,
      ConsulConfiguration consulConfiguration, Environment environment) {


    this.consulClient = consulClient;
    this.appEnv = appEnv;
    this.consulConfiguration = consulConfiguration;
    if (environment != null) {
      Collection<PropertySourceLoader> loaders = environment.getPropertySourceLoaders();
      for (PropertySourceLoader loader : loaders) {
        Set<String> extensions = loader.getExtensions();
        for (String extension : extensions) {
          loaderByFormatMap.put(extension, loader);
        }
      }
    }
  }

  @Override
  public String getDescription() {
    return consulClient.getDescription();
  }

  @Override
  public Publisher<PropertySource> getPropertySources(Environment environment) {
    if (!consulConfiguration.getConfiguration().isEnabled()) {
      return Publishers.empty();
    }
    String appID = appEnv.getAppID();
    String serviceID = appEnv.getServiceID();
    String env = appEnv.getEnv();
    String idc = appEnv.getIdc();
    String v1 = appEnv.getVersion();
    String v2 = JalorVersion.getVersion();

    String baseContext = prefix + "/" + appID + "/" + idc;

    LOG.info("Service info AppID={},Service={}({}),ENV={},IDC={},MOS={}", appID, serviceID, v1, env, idc, v2);

    List<Flux<List<KeyValue>>> keyValueFlowables = new ArrayList<>();
    Function<Throwable, Publisher<? extends List<KeyValue>>> errorHandler = throwable -> {
        if (throwable instanceof HttpClientResponseException) {
            HttpClientResponseException httpClientResponseException = (HttpClientResponseException) throwable;
            if (httpClientResponseException.getStatus() == HttpStatus.NOT_FOUND) {
                return Flux.empty();
            }
        }
        return Flux.error(new ConfigurationException("Error reading distributed configuration from Consul: " + throwable.getMessage(), throwable));
    };

    Map<String, Integer> orders = new HashMap<>();

    int priority = EnvironmentPropertySource.POSITION + 100;

    // FIXME:支持多数据中心
    String dc = null;

    // 1.服务配置
    String serviceConfigPath = baseContext + "/" + serviceID + "/application-" + v1 + "-" + env;
    orders.put(serviceConfigPath, priority++);

    Flux<List<KeyValue>> configurationValues =
        Flux.from(consulClient.readValues(serviceConfigPath, dc, null, null))
            .onErrorResume(errorHandler);
    keyValueFlowables.add(configurationValues);

    // 2.项目配置
    String appConfigPath = baseContext + "/application-" + env;
    orders.put(appConfigPath, priority++);

    configurationValues = Flux.concat(configurationValues,
    		Flux.from(consulClient.readValues(appConfigPath, dc, null, null))
            .onErrorResume(errorHandler));
    
    keyValueFlowables.add(configurationValues);

    // 3.平台配置
    String mosConfigPath = prefix + "/application-" + v2 + "-" + env;
    orders.put(mosConfigPath, priority++);

    configurationValues = Flux.concat(configurationValues,
    		Flux.from(consulClient.readValues(mosConfigPath, dc, null, null))
            .onErrorResume(errorHandler));
    
    keyValueFlowables.add(configurationValues);

    Flux<PropertySource> propertySourceFlowable =
    		Flux.merge(keyValueFlowables).flatMap(keyValues -> Flux.create(emitter -> {
          if (CollectionUtils.isEmpty(keyValues)) {
            emitter.complete();
          } else {
            Map<String, Map<String, Object>> propertySources = new HashMap<>();
            Base64.Decoder base64Decoder = Base64.getDecoder();

            for (KeyValue keyValue : keyValues) {
              String key = keyValue.getKey();
              String value = keyValue.getValue();

              PropertySourceLoader propertySourceLoader = new YamlPropertySourceLoader();
              if (propertySourceLoader.isEnabled()) {
                byte[] decoded = base64Decoder.decode(value);
                Map<String, Object> properties = propertySourceLoader.read(key, decoded);
                Map<String, Object> values =
                    propertySources.computeIfAbsent(key, s -> new LinkedHashMap<>());
                values.putAll(properties);
              }
            }

            for (Map.Entry<String, Map<String, Object>> entry : propertySources.entrySet()) {
              String name = entry.getKey();
              emitter.next(PropertySource.of(ConsulClient.SERVICE_ID + '-' + name,
                  entry.getValue(), orders.get(entry.getKey())));
            }
            emitter.complete();
          }
        },  FluxSink.OverflowStrategy.ERROR));

    propertySourceFlowable = propertySourceFlowable.onErrorResume(throwable ->

    {
      if (throwable instanceof ConfigurationException) {
        return Flux.error(throwable);
      } else {
        return Flux.error(new ConfigurationException(
            "Error reading distributed configuration from Consul: " + throwable.getMessage(),
            throwable));
      }
    });
    if (executionService != null) {
      return propertySourceFlowable
          .subscribeOn(Schedulers.fromExecutor(executionService));
    } else {
      return propertySourceFlowable;
    }
  }

  /**
   * @param executionService The execution service
   */
  @Inject
  void setExecutionService(@Named(TaskExecutors.IO) @Nullable ExecutorService executionService) {
    if (executionService != null) {
      this.executionService = executionService;
    }
  }

}
