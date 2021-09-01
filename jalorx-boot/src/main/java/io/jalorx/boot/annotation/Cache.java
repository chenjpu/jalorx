package io.jalorx.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set response Cache-Control header automatically.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
public @interface Cache {

	/**
	 * 有效期,直接在配置文件中设置缓存有效期，避免硬编码在代码中
	 * 
	 * expiration time in seconds
	 * 
	 * 0 - never expire
	 * 
	 * @return
	 */
	@Deprecated
	long expire() default 8 * 3600;

	/**
	 * 模块名
	 * 
	 * @return 返回缓存模块名
	 */
	String name();

	/**
	 * The bean name of the custom
	 * {@link org.springframework.cache.interceptor.KeyGenerator} to use.
	 * <p>
	 * Mutually exclusive with the {@link #key} attribute.
	 * 
	 * @see CacheConfig#keyGenerator
	 */
	String keyGenerator() default "";

	/**
	 * The bean name of the custom {@link org.springframework.cache.CacheManager} to
	 * use to create a default
	 * {@link org.springframework.cache.interceptor.CacheResolver} if none is set
	 * already.
	 * <p>
	 * Mutually exclusive with the {@link #cacheResolver} attribute.
	 * 
	 * @see org.springframework.cache.interceptor.SimpleCacheResolver
	 * @see CacheConfig#cacheManager
	 */
	String cacheManager() default "";

	/**
	 * The bean name of the custom
	 * {@link org.springframework.cache.interceptor.CacheResolver} to use.
	 * 
	 * @see CacheConfig#cacheResolver
	 */
	String cacheResolver() default "";
}
