package io.jalorx.boot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.UncertifiedException;
import io.jalorx.boot.utils.AuthInfoUtils;
import io.micronaut.security.rules.SecurityRule;

public abstract class AbstractSecurityRule implements SecurityRule {

	private static final Logger logger = LoggerFactory.getLogger(AbstractSecurityRule.class);

	/**
	 * 检查用户是否登录
	 * 
	 * @return
	 * @throws UncertifiedException
	 */
	protected boolean checkLogin() {
		boolean logined = AuthInfoUtils.isLogin();
		if (!logined) {
			logger.info("user not logged in");
		}
		return logined;
	}
}
