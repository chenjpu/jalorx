package io.jalorx.boot.datasource;

import static io.micronaut.configuration.metrics.micrometer.MeterRegistryFactory.MICRONAUT_METRICS_BINDERS;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micronaut.configuration.jdbc.hikari.DatasourceFactory;
import io.micronaut.configuration.jdbc.hikari.HikariUrlDataSource;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanLocator;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.transaction.jdbc.DataSourceTransactionManager;

@Factory
public class DynamicDataSourceFactory implements AutoCloseable {

	private static final Logger       LOG         = LoggerFactory.getLogger(DatasourceFactory.class);
	private List<HikariUrlDataSource> dataSources = new ArrayList<>(2);

	private ApplicationContext applicationContext;

	/**
	 * Default constructor.
	 * 
	 * @param applicationContext The application context
	 */
	public DynamicDataSourceFactory(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Context
	@Primary
	@Requires(property = DynamicDatasourceConfiguration.PREFIX)
	public DataSourceTransactionManager dataSourceTransactionManager(RoutingDataSourceWrapper localDatasource) {
		return new DataSourceTransactionManager(localDatasource.getDataSource());
	}

	@Context
	@Requires(property = DynamicDatasourceConfiguration.PREFIX)
	public RoutingDataSourceWrapper routingDataSourceWrapper(BeanLocator beanLocator) {
		return new RoutingDataSourceWrapper(new DynamicRoutingDataSource(beanLocator));
	}

	@Context
	@EachBean(DynamicDatasourceConfiguration.class)
	public DynamicDatasource dataSource(DynamicDatasourceConfiguration c) {
		HikariUrlDataSource ds = new HikariUrlDataSource(c);
		addMeterRegistry(ds);
		dataSources.add(ds);
		return new DynamicDatasource(c.getName(), c.isMaster(), ds);
	}

	private void addMeterRegistry(HikariUrlDataSource ds) {
		try {
			MeterRegistry meterRegistry = getMeterRegistry();
			if (ds != null && meterRegistry != null && this.applicationContext
					.getProperty(MICRONAUT_METRICS_BINDERS + ".jdbc.enabled", boolean.class)
					.orElse(true)) {
				ds.setMetricRegistry(meterRegistry);
			}
		} catch (NoClassDefFoundError ignore) {
			LOG.debug("Could not wire metrics to HikariCP as there is no class of type MeterRegistry on the classpath, io.micronaut.configuration:micrometer-core library missing.");
		}
	}

	private MeterRegistry getMeterRegistry() {
		return this.applicationContext.containsBean(MeterRegistry.class)
				? this.applicationContext.getBean(MeterRegistry.class)
				: null;
	}

	@Override
	@PreDestroy
	public void close() {
		for (HikariUrlDataSource dataSource : dataSources) {
			try {
				dataSource.close();
			} catch (Exception e) {
				if (LOG.isWarnEnabled()) {
					LOG.warn("Error closing data source [" + dataSource + "]: " + e.getMessage(), e);
				}
			}
		}
	}
}
