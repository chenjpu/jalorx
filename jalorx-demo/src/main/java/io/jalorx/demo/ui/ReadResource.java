package io.jalorx.demo.ui;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseReadResource;
import io.jalorx.demo.model.Demo;
import io.jalorx.demo.service.DemoService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/demo")
@Resource(code = 99000, desc = "Demo Resource")
@Operation.Read
@Validated
@Tag(name = "demo")
public class ReadResource extends BaseReadResource<Demo> {

	@Inject
	private DemoService service;

	public BaseService<Demo> getService() {
		return service;
	}

	@Get("/index1")
	public List<Serializable> index1() {
		return service.getMultUsers();
	}

}
