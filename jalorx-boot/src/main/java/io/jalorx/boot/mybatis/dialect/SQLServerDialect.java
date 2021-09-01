package io.jalorx.boot.mybatis.dialect;

import io.jalorx.boot.mybatis.Dialect;

public class SQLServerDialect implements Dialect {

	@Override
	public String databaseId() {
		return "sqlserver";
	}

	public String limitAfter() {
		return " offset #{page.firstResult} rows fetch next #{page.maxResult} rows only";
	}

}
