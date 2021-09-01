/**
 * 
 */
package io.jalorx.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注部门id字段，序列化的时候提供部门额外的字段（code，name）
 * 
 * @author chenb
 *
 * @since 2.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Dep {

}
