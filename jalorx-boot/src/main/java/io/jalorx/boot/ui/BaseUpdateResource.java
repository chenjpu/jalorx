package io.jalorx.boot.ui;

import io.jalorx.boot.model.Id;
import io.jalorx.boot.service.BaseService;

/**
 * @author chenb id为Long类型资源修改
 * @param <T>
 */
public abstract class BaseUpdateResource<T extends Id<?>> extends GenericUpdateResource<T, Long> {

	protected abstract BaseService<T> getService();

}
