package io.jalorx.security.ui.org;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Menu;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.service.OrgService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/org")
@Resource(code=10120,desc = "Org Resource")
@Validated
@Menu
@Operation.Login
@Tag(name = "security/org")
public class AuthenticatedResource {

  @Inject
  OrgService service;

}
