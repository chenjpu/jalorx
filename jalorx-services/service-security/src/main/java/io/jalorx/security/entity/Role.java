package io.jalorx.security.entity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Lookup;
import io.jalorx.boot.model.Id;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 角色.
 * 
 * @author chenb
 */
@Introspected
@Validated
@MappedEntity("tpl_app_role_t")
public class Role implements Id<Long> {

	private static final long serialVersionUID = -1449084482294160348L;
	public static final String SUPER_RIGHTS = "__ALL";
	public static String ROLE_PUBLIC = "ROLE_PUBLIC";
	public static String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

	@Schema(title = "主键ID")
	@GeneratedValue
	@io.micronaut.data.annotation.Id
	private Long id;

	@Schema(title = "名称")
	@NotEmpty
	private String name; // 名称
	@Schema(title = "编码")
	private String code; // 编码
	@Schema(title = "描述")
	@NotEmpty
	private String desp;// 描述

	@Lookup(type = "STATUS")
	@Schema(title = "状态")
	private short status;
	@NotEmpty
	@Schema(title = "系统内置标识0否1是")
	@Transient
	private boolean defaultIn;

	@Schema(title = "创建时间")
	private LocalDateTime createDate;
	@Schema(title = "最后修改时间")
	private LocalDateTime lastUpdateDate;

	@Schema(title = "项目名称")
	private String appName = "";
	@Schema(title = "多租户id")
	private String tenantId = "";
	@Schema(title = "项目群")
	private String appScope = "";

	/**
	 * @return the status
	 */
	public short getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(short status) {
		this.status = status;
	}

	/**
	 * @return the defaultIn
	 */
	public boolean isDefaultIn() {
		return defaultIn;
	}

	/**
	 * @param defaultIn the defaultIn to set
	 */
	public void setDefaultIn(boolean defaultIn) {
		this.defaultIn = defaultIn;
	}

	/**
	 * @return the superRights
	 */
	public static String getSuperRights() {
		return SUPER_RIGHTS;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
