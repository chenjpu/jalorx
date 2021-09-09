package io.jalorx.simple.ui;

import jakarta.inject.Inject;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseCreateResource;
import io.jalorx.simple.model.Demo;
import io.jalorx.simple.service.DemoService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/demo")
@Resource(code = 99000, desc = "Demo Resource")
@Operation.Anonymous
@Validated
@Tag(name = "demo")
public class CreateResource extends BaseCreateResource<Demo> {

	@Inject
	private DemoService service;

	public BaseService<Demo> getService() {
		return service;
	}

	@Post("/single1")
	@Operation.Create
	@io.swagger.v3.oas.annotations.Operation(summary = "对象创建")
	public Demo create1(@NotNull @Body Demo entity) throws BusinessAccessException {
		getService().save(entity);
		return entity;
	}

}
