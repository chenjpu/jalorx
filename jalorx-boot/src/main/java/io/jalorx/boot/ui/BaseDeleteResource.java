package io.jalorx.boot.ui;

import io.jalorx.boot.model.Id;
import io.jalorx.boot.service.BaseService;

/**
 * @author chenb
 * 
 *         id为Long类型的资源删除
 * @param <T>
 */
public abstract class BaseDeleteResource<T extends Id<?>> extends GenericDeleteResource<T, Long> {

	protected abstract BaseService<T> getService();

}
