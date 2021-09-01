package io.jalorx.boot.datasource;

import javax.sql.DataSource;

class DynamicDatasource {
	private final String name;

	private final boolean master;

	private final DataSource dataSource;

	public DynamicDatasource(String name, boolean master, DataSource dataSource) {
		this.name       = name;
		this.master     = master;
		this.dataSource = dataSource;
	}

	public String getName() {
		return name;
	}

	public boolean isMaster() {
		return master;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

}
