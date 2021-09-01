package io.jalorx.boot.ui;

import io.jalorx.boot.model.Id;
import io.jalorx.boot.service.BaseService;

/**
 * @author chenb id为Long类型资源读取
 * @param <T>
 */
public abstract class BaseReadResource<T extends Id> extends GenericReadResource<T, Long> {

	protected abstract BaseService<T> getService();

}
