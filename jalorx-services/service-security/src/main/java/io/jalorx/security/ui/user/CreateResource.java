package io.jalorx.security.ui.user;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.security.PasswordEncoder;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseCreateResource;
import io.jalorx.security.entity.User;
import io.jalorx.security.service.UserService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/user")
@Resource(code=10100,desc = "User Resource")
@Validated
@Operation.Create
@Tag(name = "security/user")
public class CreateResource extends BaseCreateResource<User> {

  @Inject
  UserService service;

  @Inject
  PasswordEncoder passwordEncoder;
  
  @Override
  protected BaseService<User> getService() {
    return service;
  }
  
  
  
  
  /**
   * 创建用户
   * @param user 用户对象
   */
  @Post("/single")
  public Serializable create(@NotNull @Valid @Body User user) throws BusinessAccessException {
    // 设置新增用户默认密码规则 “帐号 + .as”
    String defaultPwd = passwordEncoder.encode(user.getAcount() + ".as");
    if (StringUtils.isBlank(user.getPassword())) {
      user.setPassword(defaultPwd);
    }
    service.save(user);
    return user.getId();
  }


  /**
   * 批量创建用户
   * @param list 用户对象集合
   */
  @Post("/batch")
  public List<Serializable> create(@NotNull @Valid @Body List<User> list) throws BusinessAccessException {
    for (User user : list) {
      String defaultPwd = passwordEncoder.encode(user.getAcount() + ".as");
      if (StringUtils.isBlank(user.getPassword())) {
        user.setPassword(defaultPwd);
      }
    }
    return service.save(list);
  }

  ///

  /**
   * 重置密码
   * @param id 用户ID
   */
  @Post(value = "/resetPwd/{id}")
  public void resetPwd(@PathVariable("id") @NotNull Long id) {
    String defaultPwd = passwordEncoder.encode(service.get(id).getAcount() + ".as");
    service.modifyPsw(defaultPwd, id);
  }

  /**
   * 用户角色绑定
   * @param userId 用户ID
   * @param roleIds 角色ID
   * @return JSONObject
   */
  @Post("/userRole/{userId}")
  @Operation(code = 91, desc = "用户角色绑定")
  public int userRoleSetting(@NotNull  @PathVariable("userId") long userId, @NotNull @Body Long[] roleIds) {
    service.userRoleSetting(userId, roleIds);
    return roleIds.length;
  }




}
