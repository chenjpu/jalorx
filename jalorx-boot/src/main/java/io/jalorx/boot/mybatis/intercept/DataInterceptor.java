package io.jalorx.boot.mybatis.intercept;

import org.apache.ibatis.session.SqlSession;

import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.Introspected;

/**
 * Marker interface for all Data related interceptors.
 * 
 * @author chenb
 *
 * @param <T> The declaring type
 * @param <R> The return type
 */
@Introspected
public interface DataInterceptor<T, R> {

	/**
	 * Intercepts a data method execution.
	 *
	 * @param sqlSession The SqlSession
	 * @param context The context
	 * @return The result
	 */
	R intercept(SqlSession sqlSession, MethodInvocationContext<T, R> context);

}
