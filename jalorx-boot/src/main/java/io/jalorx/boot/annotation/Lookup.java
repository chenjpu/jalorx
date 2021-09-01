/**
 * 
 */
package io.jalorx.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * lookup值转化注解
 * 
 * @author chenb
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@JsonFilter("lookup")
public @interface Lookup {
	/**
	 * 分类
	 * 
	 * @return 分类
	 */
	String type();

	/**
	 * 是否多值
	 * 
	 * @return 是否是级联多值
	 */
	boolean multi() default false;

}
