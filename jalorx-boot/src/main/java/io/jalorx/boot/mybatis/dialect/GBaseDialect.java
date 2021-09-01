package io.jalorx.boot.mybatis.dialect;

import io.jalorx.boot.mybatis.Dialect;

public class GBaseDialect implements Dialect {

	@Override
	public String databaseId() {
		return "gbase";
	}

	public String limitBefore() {
		return "select skip #{page.firstResult} first #{page.maxResult} * from (";
	}

	public String limitAfter() {
		return ")";
	}
}
