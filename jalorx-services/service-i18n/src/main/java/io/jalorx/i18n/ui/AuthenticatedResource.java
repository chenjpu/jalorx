package io.jalorx.i18n.ui;

import io.jalorx.boot.annotation.Menu;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.i18n.service.I18NService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/i18n")
@Resource(code = 10120, desc = "I18N Resource")
@Validated
@Menu
@Operation.Login
@Tag(name = "i18n")
public class AuthenticatedResource {

  @Inject
  I18NService service;
   
}
