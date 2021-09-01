/**
 * 
 */
package io.jalorx.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 菜单操作定义。
 * 
 * 当框架默认提供的菜单操作不能满足业务要求的时候，可以自行定义
 * 
 * 
 * @author chenb
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface Menu {
	Operation[] value() default {@Operation(code = Operation.MENU, desc = "主菜单权限")};
}
