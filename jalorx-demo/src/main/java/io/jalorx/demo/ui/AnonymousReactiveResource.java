package io.jalorx.demo.ui;

import javax.validation.constraints.NotNull;

import org.reactivestreams.Publisher;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.demo.model.Demo;
import io.jalorx.demo.service.DemoService2;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;

@Controller("/demo")
@Operation.Anonymous
@Validated
@Tag(name = "demo")
public class AnonymousReactiveResource {

	private final DemoService2 demoService;

	public AnonymousReactiveResource(DemoService2 demoService) {
		this.demoService = demoService;
	}

	// tag::read[]
	@Get("/r2dbc")
	Flux<Demo> r2dbc1() {
		return demoService.findAll(); // <1>
	}
	
	@Get("/r2dbc/{id}")
	Publisher<Demo> notTx( @PathVariable("id") @NotNull Long id) {
		return demoService.findById(id); // <1>
	}
	
}
