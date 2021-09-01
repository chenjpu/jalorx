package io.jalorx.boot.ui;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.model.Id;
import io.jalorx.boot.service.Service;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Put;

/**
 * @author chenb
 * 
 *         基础的资源修改服务
 * 
 * @param <T>
 * @param <PK>
 */
public abstract class GenericUpdateResource<T extends Id, PK extends Serializable>
		implements
		BaseAwareResource {

	/**
	 * 修改对象 API
	 * 
	 * @param o 业务对象
	 * @return 业务对象唯一标识符
	 * @throws BusinessAccessException 业务异常
	 */
	@Put("/single")
	@Operation.Update
	@io.swagger.v3.oas.annotations.Operation(summary = "对象修改")
	public Serializable update(@Valid @Body T o) throws BusinessAccessException {
		getService().update(o);
		return o.getId();
	}

	/**
	 * 批量修改对象 API：
	 * 
	 * @param list 业务对象集合
	 * @throws BusinessAccessException 业务异常
	 */
	@Put("/batch")
	@Operation.Update
	@io.swagger.v3.oas.annotations.Operation(summary = "对象批量修改")
	public void update(@NotEmpty @Valid @Body List<T> list) throws BusinessAccessException {
		getService().update(list);
	}

	/**
	 * 资源对应的底层服务接口
	 * 
	 * @return 业务对象实现
	 */
	protected abstract Service<T, PK> getService();

}
