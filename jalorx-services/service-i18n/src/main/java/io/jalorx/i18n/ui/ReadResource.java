package io.jalorx.i18n.ui;

import java.util.List;

import io.jalorx.boot.Pair;
import io.jalorx.boot.annotation.HttpCache;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseReadResource;
import io.jalorx.i18n.entity.I18N;
import io.jalorx.i18n.service.I18NService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/i18n")
@Resource(code = 10120, desc = "I18N Resource")
@Validated
@Operation.Read
@Tag(name = "i18n")
public class ReadResource extends BaseReadResource<I18N> {

  @Inject
  I18NService service;

  @Override
  protected BaseService<I18N> getService() {
    return service;
  }
  
  @Get("/init/{languageCode}")
	@HttpCache(maxAge = 36000)
  @io.swagger.v3.oas.annotations.Operation(summary = "查询语种对应的国际化信息")
  public List<Pair> init2(@PathVariable("languageCode") String languageCode) {
     return service.getI18NMessage(languageCode);
  }

}
