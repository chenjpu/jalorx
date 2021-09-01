package io.jalorx.boot.mybatis.dialect;

import io.jalorx.boot.mybatis.Dialect;

public class OracleDialect implements Dialect {

	@Override
	public String databaseId() {
		return "oracle";
	}

	public String limitBefore() {
		return "select * from ( select a.*, ROWNUM rnum from (";
	}

	public String limitAfter() {
		return ") a where ROWNUM < #{page.lastRow}) where rnum  >= #{page.firstRow}";
	}
}
