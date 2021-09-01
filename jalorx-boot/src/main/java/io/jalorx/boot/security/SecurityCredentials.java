package io.jalorx.boot.security;

public interface SecurityCredentials {

	String getPassword();

	String getUsername();

	String getAppName();

	String getAppScope();

	String getTenantId();

}
