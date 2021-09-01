/**
 * 
 */
package io.jalorx.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.micronaut.context.annotation.AliasFor;

/**
 * 资源定义注释 在需要进行权限控制的资源类上添加该注释
 * 
 * 
 * 注意：模块的代码不能用20000以下，20000以下为系统层使用。
 * 
 * 
 * <pre>
 * &#64;Path("demo")
 * &#64;Resource(code = 99000, model = "Demo", desc = "Demo Resource")
 * public class DemoUI extends BaseResource&lt;Demo&gt; {
 * 
 * }
 * </pre>
 * 
 * @author chenb
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
// @Around
// @Type(OperationInterceptor.class)
// @Blocking
// @Bean
// @Executable
// @DefaultScope(Singleton.class)
public @interface Resource {

	/**
	 * 受控资源的名称，默认为当前的短包名类名 web.CreateResource
	 * 
	 * @return
	 */
	String id() default "";

	/**
	 * 资源编码
	 * 
	 * @return code
	 */
	@AliasFor(member = "value")
	int code() default 0;

	@AliasFor(member = "code")
	int value() default 0;

	/**
	 * 依赖的资源操作
	 * 
	 * @return
	 */
	RelyOn[] relyOn() default {};

	/**
	 * 模块名
	 * 
	 * @return model
	 */
	String model() default "JalorX";

	/**
	 * 描述
	 * 
	 * 不提供则直接解析(${model})${code}
	 * 
	 * @return desc
	 */
	String desc() default "";

}
