package io.jalorx.demo.client;


import java.util.List;

import io.jalorx.boot.Pair;
import io.micronaut.http.client.annotation.Client;

@Client(id = "demo", path = "/demo")
public interface DemoClient extends Operations {

	@Override
	String test();

	@Override
	List<Pair> test2();
}
