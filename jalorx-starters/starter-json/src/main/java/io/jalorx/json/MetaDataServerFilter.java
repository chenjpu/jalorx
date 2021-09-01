package io.jalorx.json;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jalorx.boot.json.MetaDataCollector;
import io.jalorx.boot.utils.MetaUtils;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.security.rules.SensitiveEndpointRule;
import reactor.core.publisher.Flux;

@Filter("/**")
public class MetaDataServerFilter implements HttpServerFilter {

  static Logger LOG = LoggerFactory.getLogger(MetaDataServerFilter.class);

  private final MetaDataCollector<? extends Annotation, ?>[] metaDataFilters;
  private final ObjectMapper mapper;

  public MetaDataServerFilter(MetaDataCollector<? extends Annotation, ?>[] metaDataFilters,
      ObjectMapper mapper) {
    this.metaDataFilters = metaDataFilters;
    this.mapper = mapper;
  }


  public int getOrder() {
    return SensitiveEndpointRule.ORDER + 10;
  }

  public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {

    if (request.getMethod() != HttpMethod.GET) {
      return chain.proceed(request);
    }

    Optional<String> metaHead = request.getHeaders().getFirst(MetaUtils.X_MOS_META);

    if (!MetaUtils.isMetaEnable(metaHead)) {
      LOG.debug("Meta disabled by request");
      return chain.proceed(request);
    }

    return Flux.from(chain.proceed(request)).doOnNext(response -> {
      if (MetaUtils.isMetaBodyResponse(metaHead)) {
        response.header("x-mos-meta", "1");
        return;
      }

      for (MetaDataCollector<? extends Annotation, ?> t : metaDataFilters) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Collector meta header data for type [{}]", t.annoType().getName());
        }
        request.getAttribute(t.annoType().getName(), Set.class).ifPresent((ids) -> {
          try {
            byte[] src = mapper.writeValueAsBytes(ids);
            String name = t.annoType().getSimpleName();
            response.header("x-mos-meta-" + name, new String(src));
          } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
          }
        });
      }
    });
  }
}

