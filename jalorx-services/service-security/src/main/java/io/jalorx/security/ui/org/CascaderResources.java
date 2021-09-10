package io.jalorx.security.ui.org;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.TreeNode;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.CascaderService;
import io.jalorx.boot.ui.BaseCascaderResource;
import io.jalorx.boot.utils.StringUtils;
import io.jalorx.security.service.OrgService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/security/org/cascader")
@Resource(code=10120,desc = "Org Resource")
@Validated
@Operation.Login
@Tag(name = "security/org")
public class CascaderResources extends BaseCascaderResource {

  @Inject
  OrgService service;
  
  @Override
  protected CascaderService<Long> getService() {
    return service;
  }


  /**
   * 根据pId查询组的键值对信息
   * 
   * @param pId
   * @return
   */
  @Get("/list")
  public List<TreeNode> list(@NotNull @QueryValue Long pId) throws BusinessAccessException {
    return service.getChildrenByPId(pId);
  }


  /**
   * 根据ids集合查询信息
   * 
   * @param pId
   * @return
   */
  @Get("/tree")
  @Operation.Login
  public List<TreeNode> initTree(@NotNull @QueryValue String ids) {
    return service.getAllByIds(StringUtils.toLongIds(ids));
  }



}

