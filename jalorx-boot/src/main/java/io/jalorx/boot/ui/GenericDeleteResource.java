package io.jalorx.boot.ui;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ArrayUtils;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.model.Id;
import io.jalorx.boot.service.Service;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenb
 * 
 *         基础的资源删除服务
 * 
 * @param <T>
 * @param <PK>
 */
public abstract class GenericDeleteResource<T extends Id<?>, PK extends Serializable>
		implements
		BaseAwareResource {

	/**
	 * 删除对象 API：
	 * 
	 * @param id 业务标识符
	 * @throws BusinessAccessException 业务异常
	 */
	@Delete("/single/{id}")
	@Operation.Delete
	@io.swagger.v3.oas.annotations.Operation(summary = "根据主键ID删除对象")
	public void remove(
			@Parameter(description = "primary id", example = "100") @PathVariable("id") @NotNull PK id)
			throws BusinessAccessException {
		getService().remove(id);
	}

	/**
	 * 批量删除 API：
	 * 
	 * @param ids 逗号分隔的业务标识符id
	 * @throws BusinessAccessException 业务异常
	 */
	@Delete("/batch/{ids}")
	@Operation.Delete
	@io.swagger.v3.oas.annotations.Operation(summary = "根据主键ID集合批量删除对象")
	public void removes(
			@Parameter(description = "comma-separated id", example = "100,200", schema = @Schema(type = "string")) @PathVariable("ids") PK[] ids)
			throws BusinessAccessException {
		if (ArrayUtils.isEmpty(ids)) {
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST);
		}
		getService().remove(ids);
	}

	/**
	 * 资源对应的底层服务接口
	 * 
	 * @return 业务对象实现
	 */
	protected abstract Service<T, PK> getService();

}
