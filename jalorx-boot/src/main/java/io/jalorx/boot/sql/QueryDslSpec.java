package io.jalorx.boot.sql;

import io.micronaut.data.repository.jpa.criteria.PredicateSpecification;

/**
 * 通过请求参数转化查询器
 * 
 * @author chenjpu
 *
 */
public interface QueryDslSpec {

	<T> PredicateSpecification<T> from(QueryDsl queryDsl);
}
