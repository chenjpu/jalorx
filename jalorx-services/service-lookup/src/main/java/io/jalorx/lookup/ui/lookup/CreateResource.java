package io.jalorx.lookup.ui.lookup;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseCreateResource;
import io.jalorx.lookup.entity.Lookup;
import io.jalorx.lookup.service.LookupService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/lookup")
@Resource(code = 10301, desc = "Lookup Resource")
@Validated
@Operation.Create
@Tag(name = "lookup")
public class CreateResource extends BaseCreateResource<Lookup> {

  @Inject
  LookupService service;

  @Override
  protected BaseService<Lookup> getService() {
    return service;
  }

}
