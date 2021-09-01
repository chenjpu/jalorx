package io.jalorx.security.ui.role;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.ui.BaseUpdateResource;
import io.jalorx.security.entity.Role;
import io.jalorx.security.service.RoleService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/role")
@Resource(code=10102,desc = "Role Resource")
@Validated
@Operation.Update
@Tag(name = "security/role")
public class UpdateResource extends BaseUpdateResource<Role> {

  @Inject
  RoleService service;

  @Override
  protected RoleService getService() {
    return service;
  }

}
