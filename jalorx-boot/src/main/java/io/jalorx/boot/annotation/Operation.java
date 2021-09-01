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
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

/**
 * 资源操作定义
 * 
 * 结合资源定义，完成权限控制
 * 
 * 注意： Operation提供000到006一共7个标准动作权限，200以下为平台权限，不要使用。
 * 
 * @author chenb
 * @author Bruce 如果增加了只读状态，请在security工程的SecurityBinder中加入相关判断，只读状态无需写入action<br>
 */
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Operation {

	/**
	 * 菜单
	 */
	int MENU = 0;

	/**
	 * 创建
	 */
	int CREATE = 1;

	/**
	 * 更新
	 */
	int UPDATE = 2;

	/**
	 * 读
	 */
	int READ   = 3;
	/**
	 * 删除
	 */
	int DELETE = 4;

	/**
	 * 创建流程
	 */
	int CREATE_PROCESS = 5;

	/**
	 * 审批流程
	 */
	int APPROVE_PROCESS = 6;

	/**
	 * 文件上传
	 */
	int FILE_UPLOAD = 7;

	/**
	 * 文件下载
	 */
	int FILE_DOWNLOAD = 8;

	/**
	 * 待办列表
	 */
	int TODO = 101;

	/**
	 * 已办列表
	 */
	int DONE = 102;

	/**
	 * 参与列表
	 */
	int HANDIN = 103;

	/**
	 * 发起列表
	 */
	int SPOON = 104;

	/**
	 * 监控列表
	 */
	int MONITOR = 105;

	/// .....

	/**
	 * 草稿箱
	 */
	int DRAFT = 110;

	@AliasFor(member = "value")
	int code() default 0;

	@AliasFor(member = "code")
	int value() default 0;

	/**
	 * 依赖的资源操作
	 * 
	 * @return
	 */
	// RelyOn[] relyOn() default {};

	String desc() default "";

	@Target({ElementType.METHOD,ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	@Secured(SecurityRule.IS_AUTHENTICATED)
	public @interface Login {

	}

	@Target({ElementType.METHOD,ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	@Secured(SecurityRule.IS_ANONYMOUS)
	public @interface Anonymous {

	}

	@Target({ElementType.METHOD,ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	@Operation(code = READ, desc = "读取权限")
	public @interface Read {

	}

	@Target({ElementType.METHOD,ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	@Operation(code = UPDATE, desc = "更新权限")
	public @interface Update {

	}

	@Target({ElementType.METHOD,ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	@Operation(code = CREATE, desc = "创建权限")
	public @interface Create {

	}

	@Target({ElementType.METHOD,ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	@Operation(code = DELETE, desc = "删除权限")
	public @interface Delete {

	}

}
