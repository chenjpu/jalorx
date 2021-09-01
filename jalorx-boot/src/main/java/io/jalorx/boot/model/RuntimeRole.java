package io.jalorx.boot.model;

import java.io.Serializable;

import io.micronaut.core.annotation.Introspected;

/**
 * 运行时角色 包含用户的功能角色和数据规则
 * 
 * @author chenb
 *
 */
@Introspected
public class RuntimeRole {

	private Serializable roleID;
	private Serializable dataID;

	public Serializable getRoleID() {
		return roleID;
	}

	public void setRoleID(Serializable roleID) {
		this.roleID = roleID;
	}

	public Serializable getDataID() {
		return dataID;
	}

	public void setDataID(Serializable dataID) {
		this.dataID = dataID;
	}

	@Override
	public String toString() {
		return roleID + "-" + dataID;

	}
}
