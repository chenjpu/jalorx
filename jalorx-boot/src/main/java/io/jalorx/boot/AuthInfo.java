/**
 * 
 */
package io.jalorx.boot;

import java.io.Serializable;
import java.util.Collection;

/**
 * 当前登录用户及授权
 * 
 * @author chenb
 *
 */
public interface AuthInfo {

	public static final String SUBJECT = AuthInfo.class.getName() + ".SUBJECT";

	/**
	 * 用户id
	 * 
	 * @return 用户id
	 */
	long getUserId();

	/**
	 * 用户id
	 * 
	 * @return 用户id
	 */
	String getStringUserId();

	/**
	 * 用户账号
	 * 
	 * @return 用户账号
	 */
	String getAccount();

	/**
	 * email地址
	 * 
	 * @return
	 */
	String getEmail();

	/**
	 * 语言
	 */
	String getLanguage();

	/**
	 * 扩展属性
	 * 
	 * @param name
	 * @return
	 */
	String getAttr(String name);

	/**
	 * 设置扩展属性
	 * 
	 * @param name
	 * @param value
	 */
	String setAttr(String name, String value);

	/**
	 * 用户角色
	 * 
	 * @return 用户角色
	 */
	Collection<String> getRoles();

	/**
	 * 用户权限
	 * 
	 * @return 用户权限
	 */
	Collection<String> getPermissions();

	/**
	 * 获取行级权限
	 * 
	 * @param id
	 * @return
	 */
	RowRule getRowRule(String id);

	/**
	 * 获取列级权限
	 * 
	 * @return
	 */
	Collection<String> getColRule(String id);

	/**
	 * 切换角色
	 * 
	 * @param id
	 */
	void switchRole(String id);

	/**
	 * 角色合并
	 * 
	 * @return 是否合并角色
	 */
	boolean isRoleMerged();

	/**
	 * 是否超级管理员
	 * 
	 * @return
	 */
	boolean isRoot();

	/**
	 * 是否默认的所有数据访问权限
	 * 
	 * @return
	 */
	boolean isDataALL();

	/**
	 * 当前登录角色
	 * 
	 * role_ID-dataID
	 * 
	 * @return 当前角色
	 */
	String getCurrentRole();

	/**
	 * 是否有功能权限
	 * 
	 * @param permission 功能权限
	 * @return 如果有参数指定的功能权限返回true否则返回false
	 */
	boolean isPermitted(Serializable permission);

	/**
	 * 获取多租户id值
	 * 
	 * @return
	 */
	String getTenantId();

	/**
	 * 验证密码有效性
	 * 
	 * @param pwd
	 * @return
	 */
	boolean validatePwd(String pwd);

	String getFirstname();

	String getLastname();

	String getOrginfo();

	String getAppName();

	String getAppScope();

	String getBackUp();

	String getBackUp1();

	String getBackUp2();

}
