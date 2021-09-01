package io.jalorx.lookup.ui.area;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Menu;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.lookup.service.AreaService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/lookup/area")
@Resource(code = 10302, desc = "Area Resource")
@Validated
@Menu
@Operation.Login
@Tag(name = "area")
public class AuthenticatedResource {

  @Inject
  AreaService service;

}
