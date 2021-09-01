package io.jalorx.lookup.ui.area;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseCreateResource;
import io.jalorx.lookup.entity.Area;
import io.jalorx.lookup.service.AreaService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/lookup/area")
@Resource(code = 10302, desc = "Area Resource")
@Validated
@Operation.Create
@Tag(name = "area")
public class CreateResource extends BaseCreateResource<Area> {

  @Inject
  private AreaService service;

  @Override
  protected BaseService<Area> getService() {
    return service;
  }

}
