package io.jalorx.boot.ui;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.model.Id;
import io.jalorx.boot.service.Service;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;

/**
 * @author chenb
 * 
 *         基础的资源创建服务
 * 
 * @param <T>
 * @param <PK>
 */
public abstract class GenericCreateResource<T extends Id<?>, PK extends Serializable>
		implements
		BaseAwareResource {

	/**
	 * 创建对象 API
	 * 
	 * @param entity 业务对象
	 * @return 业务对象的唯一标识符
	 * @throws BusinessAccessException 业务异常
	 */
	@Post("/single")
	@Operation.Create
	@io.swagger.v3.oas.annotations.Operation(summary = "对象创建")
	public Serializable create(@NotNull @Valid @Body T entity) throws BusinessAccessException {
		getService().save(entity);
		return entity.getId();
	}

	/**
	 * 批量创建对象 API
	 * 
	 * 
	 * @param list 业务对象集合
	 * @return 返回标识符集合
	 * @throws BusinessAccessException 业务异常
	 */
	@Post("/batch")
	@Operation.Create
	@io.swagger.v3.oas.annotations.Operation(summary = "对象批量创建")
	public Iterable<Serializable> create(@NotEmpty @Valid @Body List<T> list) throws BusinessAccessException {
		return getService().save(list);
	}

	/**
	 * 资源对应的底层服务接口
	 * 
	 * @return 业务对象实现
	 */
	protected abstract Service<T, PK> getService();

}
