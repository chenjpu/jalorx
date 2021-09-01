package io.jalorx.security.ui.user;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.ui.BaseReadResource;
import io.jalorx.security.entity.User;
import io.jalorx.security.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/user")
@Resource(code=10100,desc = "User Resource")
@Validated
@Operation.Read
@Tag(name = "security/user")
public class ReadResource extends BaseReadResource<User> {

  @Inject
  UserService service;
  
  @Override
  protected UserService getService() {
    return service;
  }

  /**
   * 用户角色ID集合查询
   * 
   * @param userId 用户ID
   * @return Long[]
   */
  @Get(value = "/userRole/{userId}")
  @Operation(code = 93, desc = "用户角色浏览")
  public Long[] getRolesByUserId(@NotEmpty @PathVariable("userId") long userId) {
        return service.getRolesByUserId(userId);
 }

}
