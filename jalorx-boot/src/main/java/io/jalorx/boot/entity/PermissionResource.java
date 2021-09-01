package io.jalorx.boot.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import io.jalorx.boot.model.LongIdVO;

/**
 * 权限资源表tpl_perm_resource_t
 */
public class PermissionResource extends LongIdVO {

	private static final long serialVersionUID = 5030442800316489341L;

	private String code;// 编码
	private int    status;// 状态
	private String desc;// 描述
	private int    revision;// 版本号
	private String appCode;// 应用(模块)编码
	private String model;

	private Set<PermissionOperation> permissionOperations = new HashSet<>();

	public PermissionResource(String appCode, String code, String desc, String model) {
		this.appCode = appCode;
		this.code    = code;
		this.desc    = desc;
		this.model   = model;
	}

	public PermissionResource() {}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Set<PermissionOperation> getPermissionOperations() {
		return permissionOperations;
	}

	public void setPermissionOperations(Set<PermissionOperation> permissionOperations) {
		this.permissionOperations = permissionOperations;
	}

	public void addAll(Collection<PermissionOperation> c) {
		this.permissionOperations.addAll(c);
	}

	public int hashCode() {
		return this.code.hashCode() << 10 + appCode.hashCode();
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		PermissionResource other = (PermissionResource) o;
		return this.appCode.equals(other.appCode) && this.code.equals(other.code);
	}
}
