package io.jalorx.boot;

public interface Account extends POJO {

	//账号锁定状态
	short LOCKED_STATUS = 0;
	//账号有效期过期状态
	short EXPIRED_STATUS = 2;
	//账号可用状态
	short ENABLE_STATUS = 1;

	Long getId();

	/**
	 * 
	 * @return 用户账号
	 */
	String getAcount();

	/**
	 * @return 用户邮箱
	 */
	String getEmail();

	/**
	 * @return 用户密码
	 */
	String getPassword();

	/**
	 * @return 用户语种
	 */
	String getLanguageCode();

	/**
	 * @return 用户姓
	 */
	String getFirstname();

	/**
	 * @return 用户名
	 */
	String getLastname();

	/**
	 * @return 用户组织结构
	 */
	String getOrginfo();

	/**
	 * @return 多租户
	 */
	String getTenantId();

	/**
	 * @return 应用名
	 */
	String getAppName();

	/**
	 * @return 应用群
	 */
	String getAppScope();

	/**
	 * @return 是否锁定状态
	 */
	boolean isLocked();

	/**
	 * @return 是否可用状态
	 */
	boolean isEnabled();

	/**
	 * @return 是否过期状态
	 */
	boolean isCredentialsExpired();

	/**
	 * @return
	 */
	String getPasswordSalt();

	/**
	 * @return root账号
	 */
	boolean isRoot();

	/**
	 * @return
	 */
	String getBackUp();

	/**
	 * @return
	 */
	String getBackUp1();

	/**
	 * @return
	 */
	String getBackUp2();

	/**
	 * @return 用户状态
	 */
	short getStatus();

	/**
	 * @param status 设置用户状态
	 */
	void setStatus(short status);
}
