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
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface HttpCache {
  int maxAge() default -1;

  int sMaxAge() default -1;

  boolean noStore() default false;

  boolean noTransform() default false;

  boolean mustRevalidate() default false;

  boolean proxyRevalidate() default false;

  boolean isPrivate() default false;

}
