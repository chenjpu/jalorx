package io.jalorx.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.jalorx.boot.security.AuthtokenAnnotationRule;
import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;
import io.micronaut.core.annotation.Blocking;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Around
@Type(AuthtokenAnnotationRule.class)
@Blocking
public @interface AuthToken {

	/**
	 * token的值
	 * 
	 * 不提供则不进行解析
	 * 
	 * @return token
	 */
	String value();

}
