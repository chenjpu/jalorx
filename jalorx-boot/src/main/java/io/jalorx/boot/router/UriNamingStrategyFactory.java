package io.jalorx.boot.router;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.web.router.RouteBuilder;
import io.micronaut.web.router.naming.ConfigurableUriNamingStrategy;
import io.micronaut.web.router.naming.HyphenatedUriNamingStrategy;
import jakarta.inject.Singleton;

/**
 * 路径策略工厂类，根据不同的配置重写默认实现
 * 
 * @author chenb
 *
 */
@Factory
public class UriNamingStrategyFactory {

	@Primary
	@Singleton
	@Replaces(ConfigurableUriNamingStrategy.class)
	@Requires(property = "micronaut.server.context-path")
	@Requires(property = "micronaut.server.service-path")
	public RouteBuilder.UriNamingStrategy contextServicePrefix(
			@Value("${micronaut.server.service-path}") String servicePath) {
		return new ServicePrefixUriNamingStrategy(servicePath);
	}

	@Primary
	@Singleton
	@Replaces(HyphenatedUriNamingStrategy.class)
	@Requires(missingProperty = "micronaut.server.context-path")
	@Requires(property = "micronaut.server.service-path")
	public RouteBuilder.UriNamingStrategy servicePrefix(@Value("${micronaut.server.service-path}") String servicePath) {
		return new ServicePrefixUriNamingStrategy(servicePath);
	}
}
