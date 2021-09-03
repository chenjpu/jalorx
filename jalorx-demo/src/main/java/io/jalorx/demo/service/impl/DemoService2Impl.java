package io.jalorx.demo.service.impl;

import javax.validation.constraints.NotNull;

import io.jalorx.demo.dao.DemoRepository;
import io.jalorx.demo.model.Demo;
import io.jalorx.demo.service.DemoService2;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ftl
 *
 */
@Named("demoService")
public class DemoService2Impl  implements DemoService2 {

	@Inject
	DemoRepository dao;
	
	@Override
	public Mono<Demo> findById(@NotNull Long aLong) {
		return dao.findById(aLong);
	}

	@Override
	public Flux<Demo> findAll() {
		return dao.findAll();
	}

}
