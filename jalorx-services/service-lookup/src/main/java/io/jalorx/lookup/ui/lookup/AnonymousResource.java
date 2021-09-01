package io.jalorx.lookup.ui.lookup;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.lookup.service.LookupService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/lookup")
@Resource(10301)
@Operation.Anonymous
@Validated
@Tag(name = "lookup")
public class AnonymousResource {

  @Inject
  LookupService service;
  

}
