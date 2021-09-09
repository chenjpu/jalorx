package io.jalorx.simple.ui;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.simple.model.Demo;
import io.jalorx.simple.service.DemoService;
import io.jalorx.simple.service.PGDemoService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/demo")
@Operation.Anonymous
@Validated
@Tag(name = "demo")
public class AnonymousResource {

	@Inject
	DemoService demoService;

	@Inject
	PGDemoService pgDemoService;

	@Get("/tranz")
	public String login() {
		demoService.tranz();
		return "ok";
	}

	@Get("/pg")
	public Demo pg() {
		return pgDemoService.get(11L);
	}

	@Get("/mysql")
	public Demo mysql() {
		return demoService.get(11L);
	}
	@Get("/mysql/age")
	public Iterable<Demo> mysqlAge() {
		return demoService.getGradAge(10);
	}

	@Get("/validate")
	public Demo validate(@NotEmpty @QueryValue("id") String id,
			@NotEmpty @QueryValue("name") String name) {
		return demoService.get(11L);
	}

	@Post("/validate")
	public Demo validate(@Valid @Body Demo demo) {
		return demo;
	}

	@Post("/validates")
	public List<Demo> validates(@NotEmpty @Valid @Body List<Demo> demo) {
		return demo;
	}
}
