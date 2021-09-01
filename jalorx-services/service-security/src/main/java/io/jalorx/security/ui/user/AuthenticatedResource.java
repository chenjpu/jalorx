package io.jalorx.security.ui.user;

import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.AuthInfo;
import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Menu;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.security.PasswordEncoder;
import io.jalorx.boot.utils.AuthInfoUtils;
import io.jalorx.security.entity.User;
import io.jalorx.security.service.UserService;
import io.jalorx.security.vo.Pwd;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/user")
@Resource(code=10100,desc = "User Resource")
@Validated
@Operation.Login
@Menu
@Tag(name = "security/user")
public class AuthenticatedResource {

  @Inject
  UserService service;
  @Inject
  PasswordEncoder passwordEncoder;

  /**
   * 修改当前用户密码
   * 
   * @param pwd
   */
  @Put("/modifyPwd")
  public void modifyPsw(@NotNull @Valid @Body Pwd pwd) {
    AuthInfo acount = AuthInfoUtils.getAuthInfo();

    if (!acount.validatePwd(passwordEncoder.encode(pwd.getOldPwd()))) {
      throw new BusinessAccessException(ErrCode.A_PASSWORD_ERROR);
    }

    service.modifyPsw(passwordEncoder.encode(pwd.getNewPwd()), acount.getUserId());
  }

  /**
   * 修改当前用户信息
   * 
   * @param user
   */
  @Put(value = "/modify")
  public void modify(@NotNull @Valid @Body User user) {
    AuthInfo acount = AuthInfoUtils.getAuthInfo();
    // 需要防止用户伪造数据修改他人账户信息
    user.setId(acount.getUserId());
    user.setAcount(acount.getAccount());
    service.update(user);
  }


  /**
   * 根据id集合查询用户基本信息
   * 
   * @param ids id集合
   * @return
   */
  @Post("/ext/attrs")
  public List<User.Meta> getAttrsUserInfo(@NotEmpty @Body Set<String> ids) {
    return service.getDetails(ids);
  }

  /**
   * 根据id集合查询用户全量信息
   * 
   * @param ids ID集合
   * @return
   */
  @Post("/usersInfo")
  public List<User> getUsersInfo(@NotEmpty @Body Long[] ids) {
    return service.get(ids);
  }
  
  /**
   * 根据账户查询用户信息
   * 
   * @param userAcount 用户账户
   * @return User
   */
  @Get("/userByAcount/{userAcount}")
  public User getUserByAcount(@PathVariable("userAcount") @NotEmpty String userAcount) {
    return service.findByUserAcount(userAcount);
  }
}
