package io.jalorx.boot.security;

public class JalorXSecurityToken {

	private final String appName;
	private final String appScope;
	private final String tenantId;

	public JalorXSecurityToken(SecurityCredentials credentials) {
		appName  = credentials.getAppName();
		appScope = credentials.getAppScope();
		tenantId = credentials.getTenantId();
	}

	public String getAppName() {
		return appName;
	}

	public String getAppScope() {
		return appScope;
	}

	public String getTenantId() {
		return tenantId;
	}

}
