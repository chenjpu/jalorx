package io.jalorx.boot.mybatis.binding;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;

import io.jalorx.boot.mybatis.intercept.DataInterceptor;
import io.micronaut.aop.MethodInvocationContext;

public class MyBatisRepositoryProxy {

	private final Class<?>                                              mapper;
	private final SqlSession                                            sqlSession;
	private final Map<MapperMethodKey, DataInterceptor<Object, Object>> interceptorMap = new ConcurrentHashMap<>(20);

	public MyBatisRepositoryProxy(Class<?> mapper, SqlSession sqlSession) {
		this.mapper     = mapper;
		this.sqlSession = sqlSession;
	}

	private DataInterceptor<Object, Object> cachedInvoker(
			MethodInvocationContext<Object, Object> context) {
		MapperMethodKey key = new MapperMethodKey(context.getTarget(), context.getExecutableMethod());
		return interceptorMap.computeIfAbsent(key, m -> {
			return new PlainMethodInvoker(
					new MapperMethod(mapper, context.getExecutableMethod(), sqlSession.getConfiguration()));
		});
	}

	private static class PlainMethodInvoker implements DataInterceptor<Object, Object> {
		private final MapperMethod mapperMethod;

		public PlainMethodInvoker(MapperMethod mapperMethod) {
			this.mapperMethod = mapperMethod;
		}

		@Override
		public Object intercept(SqlSession sqlSession,
				MethodInvocationContext<Object, Object> context) {
			return mapperMethod.execute(sqlSession, context.getParameterValues());
		}
	}

	public Object invoke(MethodInvocationContext<Object, Object> context) {
		return cachedInvoker(context).intercept(sqlSession, context);
	}
}
