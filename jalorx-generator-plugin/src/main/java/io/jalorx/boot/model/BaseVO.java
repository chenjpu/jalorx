/*
 * 
 * 创建日期：2010-4-16 上午11:28:52
 *
 * 创 建 人 ：chenjpu
 * 
 * 版权所有：J.Bob
 */

package io.jalorx.boot.model;

import java.time.LocalDateTime;

import io.jalorx.boot.POJO;

/**
 * @author chenb
 *
 */
public abstract class BaseVO implements POJO {

  /**
   * 
   */
  private static final long serialVersionUID = 3617220118055104348L;



  // protected String tenantId;// 多租户
  // protected String scope;//
  protected int revision = 1;// 版本

  protected String createUserId;
  protected LocalDateTime createDate;
  protected String lastUpdateUserId;
  protected LocalDateTime lastUpdateDate;
  
  protected String appName;
  protected String tenantId;
  protected String appScope;
  public int getRevision() {
    return revision;
  }
  public void setRevision(int revision) {
    this.revision = revision;
  }
  public String getCreateUserId() {
    return createUserId;
  }
  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }
  public LocalDateTime getCreateDate() {
    return createDate;
  }
  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }
  public String getLastUpdateUserId() {
    return lastUpdateUserId;
  }
  public void setLastUpdateUserId(String lastUpdateUserId) {
    this.lastUpdateUserId = lastUpdateUserId;
  }
  public LocalDateTime getLastUpdateDate() {
    return lastUpdateDate;
  }
  public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }
  public String getAppName() {
    return appName;
  }
  public void setAppName(String appName) {
    this.appName = appName;
  }
  public String getTenantId() {
    return tenantId;
  }
  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }
  public String getAppScope() {
    return appScope;
  }
  public void setAppScope(String appScope) {
    this.appScope = appScope;
  }
}
