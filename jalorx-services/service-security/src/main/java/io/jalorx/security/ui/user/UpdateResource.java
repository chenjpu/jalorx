package io.jalorx.security.ui.user;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.ui.BaseUpdateResource;
import io.jalorx.security.entity.User;
import io.jalorx.security.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/user")
@Resource(code=10100,desc = "User Resource")
@Validated
@Operation.Update
@Tag(name = "security/user")
public class UpdateResource extends BaseUpdateResource<User> {

  @Inject
  UserService service;

  @Override
  protected UserService getService() {
    return service;
  }

  /**
   * 修改用户状态
   * @param userID
   * @param status
   * @return 新的用户状态
   */
  @Put("/{id}/status/{status}")
  public short updateUserStatus(@NotEmpty @PathVariable("id") Long id,
      @PathVariable("status") short status) {
    //FIXME:需要校验status的有效性
    User user = service.find(id);
    user.setStatus(status);
    service.update(user);
    return status;
  }

}
