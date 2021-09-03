/**
 * 
 */
package io.jalorx.boot.model;

import java.io.Serializable;

import io.jalorx.boot.POJO;
import io.micronaut.core.annotation.Introspected;

/**
 * @author chenb
 *
 *         带id字段的实体对象
 */
@Introspected
public interface Id<T extends Serializable> extends POJO {

	/**
	 * 业务对象主键id
	 * 
	 * @return 返回id值
	 */
	public abstract T getId();
}
