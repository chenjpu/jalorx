package io.jalorx.security.ui;


import io.jalorx.boot.AuthInfo;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.utils.AuthInfoUtils;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 登录UI.
 * 
 * @author chenb
 */
@Controller("/security")
@Resource(code=10100,desc = "User Resource")
@Validated
@Tag(name = "security")
public class LoginResource {


  /**
   * 获取当前登录用户的权限信息
   * 
   * @return
   */
  @Get("/authInfo")
  @Operation.Login
  public AuthInfo authInfo() {
    AuthInfo authInfo = AuthInfoUtils.getAuthInfo();
    return authInfo;
  }

}
