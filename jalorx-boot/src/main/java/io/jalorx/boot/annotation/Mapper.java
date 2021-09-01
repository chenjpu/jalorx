package io.jalorx.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.jalorx.boot.mybatis.binding.MyBatisMapperIntroductionAdvice;
import io.micronaut.aop.Introduction;
import io.micronaut.context.annotation.Type;
import jakarta.inject.Singleton;

/**
 * @author chenb
 * 
 *         mybatis增强注解，完成接口自动装配
 */
@Introduction
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Documented
@Type(MyBatisMapperIntroductionAdvice.class)
@Singleton
public @interface Mapper {}
