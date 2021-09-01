package io.jalorx.boot.mybatis.dialect;

import io.jalorx.boot.mybatis.Dialect;

public class PostgreSQLDialect implements Dialect {
	@Override
	public String databaseId() {
		return "postgres";
	}

	public String limitAfter() {
		return "LIMIT #{page.maxResult} OFFSET #{page.firstResult}";
	}
}
