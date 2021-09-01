package io.jalorx.lookup.ui.area;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.lookup.service.AreaService;
import io.micronaut.http.annotation.Controller;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/lookup/area")
@Resource(10302)
@Operation.Anonymous
@Tag(name = "area")
public class AnonymousResource {

  @Inject
  AreaService service;

}
