package io.jalorx.security.ui.role;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Menu;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.service.RoleService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/role")
@Resource(code=10102,desc = "Role Resource")
@Validated
@Menu
@Operation.Login
@Tag(name = "security/role")
public class AuthenticatedResource {

  @Inject
  RoleService service;

}
