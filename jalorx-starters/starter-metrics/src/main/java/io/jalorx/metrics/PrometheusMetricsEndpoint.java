package io.jalorx.metrics;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micronaut.configuration.metrics.annotation.RequiresMetrics;
import io.micronaut.management.endpoint.annotation.Endpoint;
import io.micronaut.management.endpoint.annotation.Read;

@Endpoint(value = "prometheus", defaultSensitive = false, defaultEnabled = true)
@RequiresMetrics
public class PrometheusMetricsEndpoint {

  private final PrometheusMeterRegistry prometheusRegistry;

  public PrometheusMetricsEndpoint(PrometheusMeterRegistry prometheusRegistry) {
    this.prometheusRegistry = prometheusRegistry;
  }

  @Read
  public String listNames() {
    return prometheusRegistry.scrape();
  }
}
