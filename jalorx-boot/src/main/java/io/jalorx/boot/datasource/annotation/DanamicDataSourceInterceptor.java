package io.jalorx.boot.datasource.annotation;

import org.apache.commons.lang3.StringUtils;

import io.jalorx.boot.datasource.DBLookupHolder;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.AnnotationValue;
import jakarta.inject.Singleton;

@Singleton
public class DanamicDataSourceInterceptor implements MethodInterceptor<Object, Object> {

	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

	// FIXME:还未实现
	private int slave = 0;

	@Override
	public Object intercept(MethodInvocationContext<Object, Object> context) {

		if (context.hasStereotype(DS.class)) {
			String name = StringUtils.defaultIfEmpty(context.findAnnotation(DS.class)
					.flatMap(AnnotationValue::stringValue)
					.orElse(""), "default");

			if (slave > 0) {
				if (context.hasStereotype(DS.Slave.class)) {
					DBLookupHolder.slave(name, slave);
				} else {
					DBLookupHolder.master(name);
				}
			} else {
				DBLookupHolder.bind(name);
			}

			try {
				return context.proceed();
			} finally {
				DBLookupHolder.release();
			}
		} else {
			return context.proceed();
		}
	}
}
