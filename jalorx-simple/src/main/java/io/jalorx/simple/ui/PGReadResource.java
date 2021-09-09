package io.jalorx.simple.ui;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseReadResource;
import io.jalorx.simple.model.Demo;
import io.jalorx.simple.service.PGDemoService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/demo/pg")
@Resource(code = 99000, desc = "Demo Resource")
@Operation.Anonymous
@Validated
@Tag(name = "demo")
public class PGReadResource extends BaseReadResource<Demo> {

	@Inject
	private PGDemoService service;

	public BaseService<Demo> getService() {
		return service;
	}

}
