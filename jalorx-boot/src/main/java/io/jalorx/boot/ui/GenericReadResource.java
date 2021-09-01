package io.jalorx.boot.ui;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.Pageable;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.model.Id;
import io.jalorx.boot.service.Service;
import io.micronaut.core.util.ArrayUtils;
import io.micronaut.http.HttpParameters;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenb 基础的读服务
 * @param <T>
 * @param <PK>
 */
public abstract class GenericReadResource<T extends Id, PK extends Serializable>
		implements
		BaseAwareResource {

	/**
	 * 对象查询
	 * 
	 * @param id 主键ID
	 * @return 对象
	 * @throws BusinessAccessException
	 */
	@Get(uri = "/single/{id}", headRoute = false)
	@Operation.Read
	@io.swagger.v3.oas.annotations.Operation(summary = "根据主键ID查询对象")
	public T get(
			@Parameter(description = "primary id", example = "100") @PathVariable("id") @NotNull PK id)
			throws BusinessAccessException {
		return getService().get(id);
	}

	/**
	 * 批量查询ids指定的业务对象 API：
	 * 
	 * @param ids 逗号分隔的id字符串
	 * @return ids指定的对象集合
	 * @throws BusinessAccessException 业务异常
	 */
	@Get(uri = "/batch/{ids}", headRoute = false)
	@Operation.Read
	@io.swagger.v3.oas.annotations.Operation(summary = "根据主键ID集合批量查询对象")
	public List<T> gets(
			@Parameter(description = "comma-separated id", example = "100,200", schema = @Schema(type = "string")) @PathVariable("ids") PK[] ids)
			throws BusinessAccessException {
		if (ArrayUtils.isEmpty(ids)) {
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST);
		}
		return getService().get(ids);
	}

	/**
	 * 分页查询 API：
	 * 
	 * @param page 页码
	 * @param pageSize 也大小
	 * @return 返回分页查询对象
	 * @throws BusinessAccessException 业务异常
	 */
	@Get(uri = "/list/{page}/{pageSize}", headRoute = false)
	@Operation.Read
	@io.swagger.v3.oas.annotations.Operation(summary = "分页查询")
	public Pageable<T> listPage(
			@Parameter(name = "Q", in = ParameterIn.QUERY, array = @ArraySchema(schema = @Schema(type = "string")), description = QE, required = false, allowEmptyValue = true) HttpParameters params,
			@PathVariable("page") int page, @PathVariable("pageSize") int pageSize)
			throws BusinessAccessException {
		return getService().getAll(getQueryFilter(params), page, pageSize);
	}

	/**
	 * 查询集合 API
	 * 
	 * @return 满足条件的对象集合
	 * @throws BusinessAccessException 业务异常
	 */
	@Get(uri = "/list", headRoute = false)
	@Operation.Read
	@io.swagger.v3.oas.annotations.Operation(summary = "批量查询")
	public List<T> list(
			@Parameter(name = "Q", in = ParameterIn.QUERY, array = @ArraySchema(schema = @Schema(type = "string")), description = QE, required = false, allowEmptyValue = true) HttpParameters params)
			throws BusinessAccessException {
		return getService().getAll(getQueryFilter(params));
	}

	/**
	 * 资源对应的底层服务接口
	 * 
	 * @return 业务对象实现
	 */
	protected abstract Service<T, PK> getService();
}
