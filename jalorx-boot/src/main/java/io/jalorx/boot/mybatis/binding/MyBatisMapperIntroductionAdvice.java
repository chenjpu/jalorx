package io.jalorx.boot.mybatis.binding;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.ClassUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.aop.Introduced;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.context.BeanLocator;
import io.micronaut.core.annotation.TypeHint;
import jakarta.inject.Singleton;

@Singleton
public final class MyBatisMapperIntroductionAdvice implements MethodInterceptor<Object, Object> {

	private static final Logger LOG = LoggerFactory.getLogger(MyBatisMapperIntroductionAdvice.class);

	private final BeanLocator                           beanLocator;
	private final MapperRegistry                        registry       = new MapperRegistry();
	private final Map<Class<?>, MyBatisRepositoryProxy> interceptorMap = new ConcurrentHashMap<>(20);

	MyBatisMapperIntroductionAdvice(BeanLocator beanLocator) {
		this.beanLocator = beanLocator;
	}

	@Override
	public Object intercept(MethodInvocationContext<Object, Object> context) {

		Class<?> targetType = getTargetType(context);

		MyBatisRepositoryProxy dataInterceptor = interceptorMap.computeIfAbsent(targetType, type -> {
			SqlSession sqlSession     = beanLocator.getBean(SqlSession.class);
			Class<?>   mapperInteface = getMapperInterfacesFor(context);
			return registry.addMapper(mapperInteface, sqlSession);
		});
		return dataInterceptor.invoke(context);
	}

	protected Class<?> getMapperInterfacesFor(MethodInvocationContext<Object, Object> context) {

		if (context.hasDeclaredAnnotation(TypeHint.class)) {
			Class<?>[] clazz = context.getDeclaredAnnotation(TypeHint.class)
					.classValues();

			if (clazz.length > 0) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Finding Mapper [{}] from TypeHint {}", clazz[0], ClassUtils
							.convertClassesToClassNames(Arrays.asList(clazz)));
				}
				return clazz[0];
			}
		}

		Class<?> type = getTargetType(context);

		Class<?>[] ifcs = type.getInterfaces();
		for (Class<?> ifc : ifcs) {
			if (!ifc.isAssignableFrom(Introduced.class)) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Finding Mapper [{}] from [{}]", ifc, type);
				}
				return ifc;
			}
		}
		LOG.error("Not Find Mapper interface for [{}] in {}", type, ClassUtils
				.convertClassesToClassNames(Arrays.asList(ifcs)));
		throw new IllegalStateException("Not available");
	}

	/**
	 * 返回代理类型
	 * 
	 * @param context
	 * @return
	 */
	private Class<?> getTargetType(MethodInvocationContext<Object, Object> context) {
		Object instance = context.getTarget();
		//Assert.notNull(instance, "Instance must not be null");
		return instance.getClass();
	}

}
