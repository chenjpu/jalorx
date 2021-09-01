package io.jalorx.boot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.UncertifiedException;
import io.jalorx.boot.utils.AuthInfoUtils;
import io.micronaut.aop.MethodInvocationContext;

public class BaseSecurityInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(BaseSecurityInterceptor.class);

	protected void checkLogin(MethodInvocationContext<Object, Object> info) {
		if (!AuthInfoUtils.isLogin()) {
			logger.info("session time out");
			throw new UncertifiedException();
		}
		// AuthInfo acount = AuthInfoUtils.getAuthInfo();
		// Authentication.setAuthenticatedUserId(String.valueOf(acount.getUserId()));
	}

}
