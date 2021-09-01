package io.jalorx.security.ui.perm;

import jakarta.inject.Inject;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.service.PermissionService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/permission")
@Resource(code=10103,desc = "Permission Resource")
@Validated
@Operation.Create
@Tag(name = "security/permission")
public class CreateResource {

  @Inject
  PermissionService service;


  /**
   * 根据角色Id 和 权限Id更新角色权限关系
   * 
   * @param roleId,ids
   * @return
   */
  @Post("/role/{roleId}")
  @Operation(code = 202, desc = "角色权限")
  public void savePermissions(@NotNull @PathVariable("roleId") long roleId,
      @NotNull @Body String[] ids) {
    service.updatePermissions(roleId, ids);
  }
}
