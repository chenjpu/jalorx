package io.jalorx.boot.model;

import io.micronaut.core.annotation.Introspected;

/**
 * 运行时角色 包含用户的功能角色和数据规则
 * 
 * @author chenb
 *
 */
@Introspected
public class RuntimeRole {

	private Long roleId;
	private Long dataId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	@Override
	public String toString() {
		return roleId + "-" + dataId;

	}
}
