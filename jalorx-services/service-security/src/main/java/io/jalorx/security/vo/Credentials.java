package io.jalorx.security.vo;

import java.io.Serializable;

import org.apache.commons.lang3.BooleanUtils;

import io.jalorx.boot.security.SecurityCredentials;

/**
 * @author chenb
 */
public class Credentials implements SecurityCredentials, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6220323019727672121L;

  /**
   * 登录用户名
   */
  private String username;
  /**
   * 登录密码
   */
  private String password;

  private String appName;

  private String appScope;

  private String tenantId;

  /**
   * 是否记住我
   */
  private boolean rememberMe;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the rememberMe
   */
  public boolean isRememberMe() {
    return rememberMe;
  }

  /**
   * @param rememberMe the rememberMe to set
   */
  public void setRememberMe(String rememberMe) {
    this.rememberMe = BooleanUtils.toBoolean(rememberMe);
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

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public void setAppScope(String appScope) {
    this.appScope = appScope;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }
}
