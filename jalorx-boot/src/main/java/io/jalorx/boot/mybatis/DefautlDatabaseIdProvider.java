package io.jalorx.boot.mybatis;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.lang3.Validate;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.transaction.jdbc.DataSourceUtils;
import io.micronaut.transaction.jdbc.DelegatingDataSource;

/**
 * @author chenb
 *
 */
public class DefautlDatabaseIdProvider implements DatabaseIdProvider {

	private static final DatabaseIdProvider INSTANCE = new DefautlDatabaseIdProvider();

	private static Logger                        LOG             = LoggerFactory
			.getLogger(DefautlDatabaseIdProvider.class);
	private static final Map<DataSource, String> databaseIdCache = new ConcurrentHashMap<>();

	private Properties properties;

	private DefautlDatabaseIdProvider() {
		Properties properties = new Properties();
		properties.put("SQL Server", "sqlserver");
		properties.put("Oracle", "oracle");
		properties.put("MySQL", "mysql");
		properties.put("PostgreSQL", "postgres");
		properties.put("IBM Informix", "gbase");
		this.properties = properties;
	}

	public static DatabaseIdProvider get() {
		return INSTANCE;
	}

	@Override
	public String getDatabaseId(DataSource dataSource) {
		Validate.notNull(dataSource);
		return databaseIdCache.computeIfAbsent(dataSource, ds -> {
			LOG.info("Init databaseId from dataSource");
			try {
				return getDatabaseName(dataSource);
			} catch (SQLException e) {
				throw new RuntimeException("Could not get a databaseId from dataSource", e);
			}
		});
	}

	private String getDatabaseName(DataSource dataSource) throws SQLException {
		String productName = getDatabaseProductName(dataSource);
		LOG.info("Database Product Name is '{}'", productName);
		for (Map.Entry<Object, Object> property : properties.entrySet()) {
			if (productName.contains((String) property.getKey())) {
				return (String) property.getValue();
			}
		}
		throw new UnsupportedOperationException("Unsupported mybaits dialect for " + productName);
	}

	private String getDatabaseProductName(DataSource dataSource) throws SQLException {
		dataSource = DelegatingDataSource.unwrapDataSource(dataSource);
		Connection con = DataSourceUtils.getConnection(dataSource);
		try {
			DatabaseMetaData metaData = con.getMetaData();
			return metaData.getDatabaseProductName();
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}

	}
}
