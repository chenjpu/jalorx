package io.jalorx.security.ui.role;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.ui.BaseCreateResource;
import io.jalorx.security.entity.Role;
import io.jalorx.security.service.RoleService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/role")
@Resource(code=10102,desc = "Role Resource")
@Validated
@Operation.Create
@Tag(name = "security/role")
public class CreateResource extends BaseCreateResource<Role> {

  @Inject
  RoleService service;

  @Override
  protected RoleService getService() {
    return service;
  }

  /**
   * 给用户集合设置单个角色
   * 
   * @param roleId 角色ID
   * @param userIds 用户ID集合
   * @return void
   */
  @Post("/roleUser/{roleId}")
  @Operation(code = 91, desc = "角色用户绑定")
  public void userRoleSetting(@NotEmpty @PathVariable("roleId")  long roleId,@NotEmpty @Body Long[] userIds) {
    service.userRoleSetting(roleId, userIds);
  }

}
