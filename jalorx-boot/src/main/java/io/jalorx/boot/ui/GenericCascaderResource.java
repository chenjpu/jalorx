package io.jalorx.boot.ui;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.TreeNode;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.service.CascaderService;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.swagger.v3.oas.annotations.Parameter;

@Produces("application/json")
@Consumes("application/json")
public abstract class GenericCascaderResource<PK extends Serializable> {

	@Get("/list/{id}")
	@Operation.Login
	@io.swagger.v3.oas.annotations.Operation(summary = "根据主键ID子节点")
	public List<TreeNode> list(
			@Parameter(description = "primary id", example = "100") @PathVariable("id") PK pId)
			throws BusinessAccessException {
		return getService().getChildrenByPId(pId);
	}

	@Get("/tree/{ids}")
	@Operation.Login
	@io.swagger.v3.oas.annotations.Operation(summary = "批量查询")
	public List<TreeNode> initTree(
			@Parameter(description = "comma-separated id", example = "100,200") @PathVariable("ids") PK[] ids) {
		if (ArrayUtils.isEmpty(ids)) {
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST);
		}
		return getService().getAllByIds(ids);
	}

	protected abstract CascaderService<PK> getService();

}
