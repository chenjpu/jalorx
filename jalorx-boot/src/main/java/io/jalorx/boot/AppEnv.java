/**
 * 
 */
package io.jalorx.boot;

import java.io.Serializable;

import io.micronaut.context.annotation.BootstrapContextCompatible;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.env.Environment;

/**
 * 引用配置参数
 * 
 * 包括引用名称，数据库名，业务表前缀，系统群
 * 
 * @author chenb
 *
 */
@ConfigurationProperties("app")
@BootstrapContextCompatible
public class AppEnv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6955087189769303995L;

	private String appID;
	private String serviceID;

	private String env;

	private String idc;

	private String version;

	/**
	 * 数据名称
	 */
	private String dbName;

	/**
	 * 表前缀名
	 */
	private String tablePrefix;
	/**
	 * 
	 */
	private String scope;

	private String tenantId;

	public AppEnv(Environment environment) {
		this.appID = getProperty(environment, "app.id", "APP_ID", "");
		String def = environment.get("micronaut.application.name", String.class, "unknow");
		this.serviceID = getProperty(environment, "service.id", "SERVICE_ID", def);
		this.env       = getProperty(environment, "registd.env", "REGISTD_ENV", "dev");
		this.idc       = getProperty(environment, "registd.idc", "REGISTD_IDC", "default");
		this.version   = getProperty(environment, "api.version", "API_VERSION", "v1");
	}

	/**
	 * @return the Name
	 */
	public String getName() {
		return appID;
	}

	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the tablePrefix
	 */
	public String getTablePrefix() {
		return tablePrefix;
	}

	/**
	 * @param tablePrefix the tablePrefix to set
	 */
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getAppID() {
		return appID;
	}

	public String getEnv() {
		return env;
	}

	public String getIdc() {
		return idc;
	}

	public String getVersion() {
		return version;
	}

	public String getServiceID() {
		return serviceID;
	}

	public static String getProperty(Environment env, String key1, String key2, String def) {
		return env.getProperty(key1, String.class, env.getProperty(key2, String.class, def));
	}

}
