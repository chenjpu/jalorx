package io.jalorx.boot.utils;

import java.io.Serializable;
import java.util.Optional;

import io.jalorx.boot.AuthInfo;
import io.jalorx.boot.UncertifiedException;
import io.jalorx.boot.security.AuthInfoContext;

/**
 * 当前登录账户的信息
 * 
 * 
 * @author chenb
 *
 */
public class AuthInfoUtils {

	/**
	 * 登录账户信息
	 * 
	 * @return
	 * @throws UncertifiedException 如果没有登录返回未登录异常
	 */
	public static AuthInfo getAuthInfo() throws UncertifiedException {
		return AuthInfoContext.current()
				.orElseThrow(() -> new UncertifiedException());
	}

	/**
	 * 判断当前用户是否登录
	 * 
	 * @return
	 */
	public static boolean isLogin() {
		return AuthInfoContext.current()
				.isPresent();
	}

	/**
	 * 当前登录用户的id
	 * 
	 * @return
	 */
	public static Serializable getCurrentUserId() {
		AuthInfo acount = getAuthInfo();
		return acount.getUserId();
	}

	/**
	 * 当前登录用户的多租户ID,没有登录返回null
	 * 
	 * @return
	 */
	public static String getCurrentTenantId() {
		return AuthInfoContext.current()
				.flatMap(a -> Optional.ofNullable(a.getTenantId()))
				.orElse(null);
	}

}
