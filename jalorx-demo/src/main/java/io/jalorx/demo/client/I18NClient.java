package io.jalorx.demo.client;

import java.util.List;

import io.jalorx.boot.Pair;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.CircuitBreaker;
import io.micronaut.retry.annotation.Retryable;

@Client(id = "i18n-jalorx-prod", path = "/i18n")
@Retryable
public interface I18NClient extends Operations {

	@Override
	String test();

	@Override
	@CircuitBreaker
	List<Pair> test2();
}
