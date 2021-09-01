package io.jalorx.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动流程
 * 
 * @author chenb
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Inherited
@Documented
public @interface StartProcess {

	/**
	 * The key of the process definition to start, as provided in the 'id' attribute
	 * of a bpmn20.xml process definition.
	 * 
	 * 如果为空，则按实体对象的简短名称作为流程id
	 * 
	 * @return 启动流程id
	 */
	String value() default "";

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	@Inherited
	@Documented
	public @interface Draft {

	}

}
