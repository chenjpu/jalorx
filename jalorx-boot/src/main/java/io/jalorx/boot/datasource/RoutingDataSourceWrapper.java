package io.jalorx.boot.datasource;

import javax.sql.DataSource;

public class RoutingDataSourceWrapper {

	private final DataSource dataSource;

	public RoutingDataSourceWrapper(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

}
