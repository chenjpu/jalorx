package io.jalorx.boot.ui;

import io.jalorx.boot.model.Id;
import io.jalorx.boot.service.BaseService;

/**
 * @author chenb
 * 
 *         id为Long类型的资源创建
 * @param <T>
 */
public abstract class BaseCreateResource<T extends Id<?>> extends GenericCreateResource<T, Long> {

	protected abstract BaseService<T> getService();

}
