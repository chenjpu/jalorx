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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jalorx.boot.POJO;
import io.jalorx.boot.annotation.User;
import io.jalorx.boot.utils.AuthInfoUtils;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Transient;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenb
 *
 */
@Introspected
public abstract class BaseVO implements POJO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3617220118055104348L;

	// protected String tenantId;// 多租户
	// protected String scope;//
	@Schema(title = "修改版本号")
	protected int revision = 1;// 版本
	@User
	@Schema(title = "创建人ID")
	protected String createUserId;
	@Schema(title = "创建时间")
	protected LocalDateTime   createDate;
	@User
	@Schema(title = "最后修改人ID")
	protected String lastUpdateUserId;
	@Schema(title = "最后修改时间")
	protected LocalDateTime   lastUpdateDate;

	@Schema(title = "项目名称")
	protected String appName;
	@Schema(title = "多租户id")
	protected String tenantId;
	@Schema(title = "项目群")
	protected String appScope;

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getAppName() {
		return appName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getAppScope() {
		return appScope;
	}

	public void setAppScope(String appScope) {
		this.appScope = appScope;
	}

	public void createInit() {
		this.createDate     = LocalDateTime.now();
		this.lastUpdateDate = LocalDateTime.now();
		this.appName        = "local";
		this.appScope       = "local";
		this.tenantId       = "global";
		if (AuthInfoUtils.isLogin()) {
			String userId = AuthInfoUtils.getAuthInfo()
					.getStringUserId();
			this.createUserId     = userId;
			this.lastUpdateUserId = userId;
		}
	}

	public void updateInit() {
		this.lastUpdateDate = LocalDateTime.now();
		if (AuthInfoUtils.isLogin()) {
			if (AuthInfoUtils.isLogin()) {
				String userId = AuthInfoUtils.getAuthInfo()
						.getStringUserId();
				this.lastUpdateUserId = userId;
			}
		}
	}

	/*
	 * public String getAppName() { return BeanContext.getAppEnv().getName(); }
	 * 
	 * public String getTenantId() { return AuthInfoUtils.getCurrentTenantId(); }
	 * 
	 * public String getScope() { return BeanContext.getAppEnv().getScope(); }
	 */

	/**
	 * @return the revision
	 */
	public int getRevision() {
		return revision;
	}

	/**
	 * @return the createUserId
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the createDate
	 */
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the lastUpdateUserId
	 */
	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	/**
	 * @param lastUpdateUserId the lastUpdateUserId to set
	 */
	public void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @param revision the revision to set
	 */
	public void setRevision(int revision) {
		this.revision = revision;
	}

	@JsonIgnore()
	@Transient
	public int getRevisionNext() {
		return revision + 1;
	}

	@JsonIgnore()
	@Transient
	public Object getCurrentUser() {
		return AuthInfoUtils.getAuthInfo();
	}

	@JsonIgnore()
	@Transient
	public long getCurrentUserId() {
		return AuthInfoUtils.getAuthInfo()
				.getUserId();
	}

}
