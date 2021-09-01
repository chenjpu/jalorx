package io.jalorx.demo.ui;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseUpdateResource;
import io.jalorx.demo.model.Demo;
import io.jalorx.demo.service.DemoService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/demo")
@Resource(code = 99000, desc = "Demo Resource")
@Validated
@Operation.Anonymous
@Tag(name = "demo")
public class UpdateResource extends BaseUpdateResource<Demo> {

	@Inject
	DemoService service;

	@Override
	protected BaseService<Demo> getService() {
		return service;
	}
}
