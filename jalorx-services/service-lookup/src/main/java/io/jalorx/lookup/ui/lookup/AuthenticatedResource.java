package io.jalorx.lookup.ui.lookup;

import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.Pair;
import io.jalorx.boot.annotation.Menu;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.lookup.entity.Lookup;
import io.jalorx.lookup.service.LookupService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/lookup")
@Resource(code = 10301, desc = "Lookup Resource")
@Validated
@Menu
@Operation.Login
@Tag(name = "lookup")
public class AuthenticatedResource {

  @Inject
  LookupService service;


  /**
   * 根据父级ID查询实体
   * @param parentId
   * @return
   */
  @Get("/{parentId}")
  public List<Lookup> lookupByParentId(@PathVariable("parentId") @NotNull Long parentId) {
    return service.lookupByParentId(parentId);

  }

  /**
   * 根据groupCode查询实体
   * @param groupCode
   * @return
   */
  @Get("/init/{groupCode}")
  public List<Lookup> lookupByGroupCode(@PathVariable("groupCode")  @NotEmpty String groupCode) {
    return service.lookupByGroupCode(groupCode);

  }
  
  /**
   * 根据id集合查询lookup基本信息
   * 
   * @param ids id集合
   * @return
   */
  @Post("/ext/attrs")
  public List<Lookup.Meta> getAttrsUserInfo(@NotEmpty @Body Set<Pair> ids) {
    return service.getDetails(ids);
  }
}
