package io.jalorx.boot.entity;

import java.util.HashSet;
import java.util.Set;

import io.jalorx.boot.model.LongIdVO;

/**
 * 权限操作码表tpl_perm_oper_t
 */
public class PermissionOperation extends LongIdVO {

	private static final long serialVersionUID = 5030442800316489341L;

	private String code;// 编码
	private int    status;// 状态
	private String desc;// 描述
	private int    revision;// 版本号

	private Long parentId;// resourceId

	private String parentCode;

	private Set<String> urls = new HashSet<>();

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

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		return this.code.equals(((PermissionOperation) o).code);
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public PermissionOperation addUrl(String url) {
		this.urls.add(url);
		return this;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	// set去重必须要的方法
	public int hashCode() {
		return this.code != null ? this.code.hashCode() : 0;
	}

}
