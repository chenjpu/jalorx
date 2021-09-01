package io.jalorx.demo.client;

import java.util.List;

import io.jalorx.boot.Pair;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;

@Validated
public interface Operations {
	@Get("/test")
	String test();

	@Get("/test21")
	List<Pair> test2();
}
