package io.jalorx.boot.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.BeanLocator;
import io.micronaut.transaction.exceptions.TransactionSystemException;

/**
 * @author chenb
 *
 */
public class DynamicRoutingDataSource extends AbstractDataSource {

	private static Logger LOG = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

	private final Map<String, DynamicDatasource> dataSourceMap = new ConcurrentHashMap<>();

	/**
	 * @param beanLocator The {@link BeanLocator}
	 */
	public DynamicRoutingDataSource(BeanLocator beanLocator) {
		Collection<DynamicDatasource> dds = beanLocator.getBeansOfType(DynamicDatasource.class);
		dds.forEach(ds -> {
			dataSourceMap.put(ds.getName(), ds);
		});
	}

	@Override
	public Connection getConnection() throws SQLException {
		return determineTargetDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return determineTargetDataSource().getConnection(username, password);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return (T) this;
		}
		return determineTargetDataSource().unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
	}

	protected DataSource determineTargetDataSource() {
		String            lookupKey  = determineCurrentLookupKey(DBLookupHolder.get());
		DynamicDatasource dataSource = resolveDataSource(lookupKey);
		return dataSource.getDataSource();
	}

	protected String determineCurrentLookupKey(String name) {
		return name;
	}

	private DynamicDatasource resolveDataSource(String dataSourceName) {
		String lookupKey = StringUtils.defaultIfEmpty(dataSourceName, "default");
		LOG.debug("Fetching datasource for name `{}`, qualifier by '{}'", dataSourceName, lookupKey);
		return dataSourceMap.computeIfAbsent(lookupKey, key -> {
			throw new TransactionSystemException("No dataSource configured for name: " + key);
		});
	}

}
