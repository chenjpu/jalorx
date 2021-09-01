package io.jalorx.security.ui.user;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.ui.BaseDeleteResource;
import io.jalorx.boot.utils.StringUtils;
import io.jalorx.security.entity.User;
import io.jalorx.security.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/user")
@Resource(code=10100,desc = "User Resource")
@Validated
@Operation.Delete
@Tag(name = "security/user")
public class DeleteResource extends BaseDeleteResource<User> {

  @Inject
  UserService service;

  /**
   * 用户角色绑定
   * 
   * @param userId 用户ID
   * @param roleIds 角色ID
   * @return void
   */
  @Delete("/userRole/{userId}/{roleIds}")
  @Operation(code = 94, desc = "用户角色解绑")
  public void delUserRolesByIds(@NotEmpty @PathVariable("userId") long userId,
      @PathVariable("roleIds") @NotEmpty String roleIds) {
    service.delUserRolesByIds(userId, StringUtils.toLongIds(roleIds));
  }

  @Override
  protected UserService getService() {
    return service;
  }

}
