package io.jalorx.boot.mybatis.dialect;

import io.jalorx.boot.mybatis.Dialect;

public class MySQLDialect implements Dialect {
	@Override
	public String databaseId() {
		return "mysql";
	}

	public String limitAfter() {
		return "LIMIT #{page.maxResult} OFFSET #{page.firstResult}";
	}
}
