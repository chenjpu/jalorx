package io.jalorx.security.ui.perm;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Menu;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.service.PermissionService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/permission")
@Resource(code=10103,desc = "Permission Resource")
@Validated
@Menu
@Operation.Login
@Tag(name = "security/permission")
public class AuthenticatedResource {

  @Inject
  PermissionService service;

}
