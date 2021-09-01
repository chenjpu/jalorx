package io.jalorx.security.ui.user;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/user")
@Resource(code = 10100, desc = "User Resource")
@Validated
@Operation.Anonymous
@Tag(name = "security/user")
public class AnonymousResource {

  @Inject
  UserService service;

  /**
   * 根据账户查询用戶是否存在
   * 
   * @param userAcount 用户账户
   * @return
   */
  @Get("/existsByAcount/{acount}")
  public boolean getUserByAcount(@PathVariable("acount") @NotEmpty String userAcount) {
    return service.findByUserAcount(userAcount) != null;
  }

}
