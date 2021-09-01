package io.jalorx.security.ui.group;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.service.GroupService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/group")
@Resource(10107)
@Validated
@Operation.Anonymous
@Tag(name = "security/group")
public class AnonymousResource {

  @Inject
  GroupService service;
 
}
