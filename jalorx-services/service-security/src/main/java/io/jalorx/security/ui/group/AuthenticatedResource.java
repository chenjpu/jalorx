package io.jalorx.security.ui.group;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Menu;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.entity.Group;
import io.jalorx.security.service.GroupService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/group")
@Resource(code=10107,desc = "Group Resource")
@Validated
@Menu
@Operation.Login
@Tag(name = "security/group")
public class AuthenticatedResource {

  @Inject
  GroupService service;
  
  
  /**
   * 根据code查询组
   * @param code
   * @return
   */
  @Get("/groupCode/{code}")
  public Group findGroupByCode(@PathVariable("code") @NotEmpty String code) {
    return service.findGroupByCode(code);
  }

  /**
   * 查询指定用户加入的组
   * @param userId
   * @return
   */
  @Get("/groupIds/{userId}")
  public Long[] getGroupIdsByUserId(@NotEmpty @PathVariable("userId") long userId) {
    return service.getGroupIdsByUserId(userId);
  }

}
