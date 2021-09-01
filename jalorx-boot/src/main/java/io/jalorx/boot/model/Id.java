/**
 * 
 */
package io.jalorx.boot.model;

import java.io.Serializable;

import io.jalorx.boot.POJO;

/**
 * @author chenb
 *
 *         带id字段的实体对象
 */
public interface Id extends POJO {

	/**
	 * 业务对象主键id
	 * 
	 * @return 返回id值
	 */
	public abstract Serializable getId();
}
