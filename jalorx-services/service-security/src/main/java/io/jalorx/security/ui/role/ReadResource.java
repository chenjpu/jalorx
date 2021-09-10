package io.jalorx.security.ui.role;

import jakarta.inject.Inject;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.ui.BaseReadResource;
import io.jalorx.security.entity.Role;
import io.jalorx.security.service.RoleService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/role")
@Resource(code=10102,desc = "Role Resource")
@Validated
@Operation.Read
@Tag(name = "security/role")
public class ReadResource extends BaseReadResource<Role> {

  @Inject
  RoleService service;
  
  @Override
  protected RoleService getService() {
    return service;
  }

  /**
   * 根据角色ID查询已绑定的用户ID集合
   * 
   * @param roleId 角色ID
   * @return Long[]
   */
  @Get("/roleUser/{roleId}")
  @Operation(code = 93, desc = "角色用户浏览")
  public List<Long> getUsersByRoleId(@NotEmpty @PathVariable("roleId") long roleId) {
    return service.getUsersByRoleId(roleId);
  }

}
