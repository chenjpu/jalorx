/**
 * 
 */
package io.jalorx.boot.ui;

import io.jalorx.boot.utils.StringUtils;

/**
 * 
 * 主键类型为long的基础资源实现
 * 
 * @author chenb
 *
 */
public abstract class BaseCascaderResource extends GenericCascaderResource<Long> {

	protected Long[] commaDelimitedListToArray(String commas) {
		return StringUtils.toLongIds(commas);
	}
}
