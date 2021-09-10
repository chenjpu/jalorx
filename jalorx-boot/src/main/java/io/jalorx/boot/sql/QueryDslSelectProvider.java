package io.jalorx.boot.sql;


/**
 * 通过请求参数转化查询器
 * 
 * @author chenjpu
 *
 */
public interface QueryDslSelectProvider {

	SelectProvider from(QueryDsl queryDsl,Class<?> clazz);
}
