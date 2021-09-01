package io.jalorx.registd;

import org.reactivestreams.Publisher;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.discovery.DiscoveryClient;
import io.micronaut.health.HealthStatus;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import io.micronaut.management.health.indicator.discovery.DiscoveryClientHealthIndicator;
import jakarta.inject.Singleton;

@Requires(beans = DiscoveryClient.class)
@Replaces(DiscoveryClientHealthIndicator.class)
@Singleton
public class RegistdDiscoveryClientHealthIndicator implements HealthIndicator {
  private final DiscoveryClient discoveryClient;

  /**
   * @param discoveryClient The Discovery client
   */
  public RegistdDiscoveryClientHealthIndicator(DiscoveryClient discoveryClient) {
    this.discoveryClient = discoveryClient;
  }

  @Override
  public Publisher<HealthResult> getResult() {
    HealthResult.Builder builder =
        HealthResult.builder(discoveryClient.getDescription(), HealthStatus.UP);
    return Publishers.just(builder.build());
  }

}
