package io.jalorx.demo.ui;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.demo.service.DemoService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/demo")
@Resource(code = 99000, desc = "Demo Resource")
@Operation.Login
@Tag(name = "security/group")
@Validated
public class AuthenticatedResource {

	@Inject
	DemoService demoService;

	@Get("/index2")
	public List<Serializable> index2() {
		return demoService.getMultUsers();
	}

}
