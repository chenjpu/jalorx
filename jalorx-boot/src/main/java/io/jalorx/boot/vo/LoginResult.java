package io.jalorx.boot.vo;

import io.jalorx.boot.security.SecurityLoginResult;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.security.authentication.Authentication;

/**
 * @author chenb
 */
@Introspected
public class LoginResult implements SecurityLoginResult {

	private String context;

	private String home;

	private int status;

	private Authentication user;

	// 用户登录状态字段，用来判断是否修改密码
	private int loginStatus;

	public int getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}

	/**
	 * @return the user
	 */
	public Authentication getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Authentication user) {
		this.user = user;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
