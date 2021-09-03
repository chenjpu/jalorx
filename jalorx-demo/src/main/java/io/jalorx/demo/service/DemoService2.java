/**
 * 
 */
package io.jalorx.demo.service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.datasource.annotation.DS;
import io.jalorx.demo.model.Demo;
import io.micronaut.transaction.annotation.ReadOnly;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ftl
 *
 */
@Transactional
@DS
public interface DemoService2 {

	@ReadOnly
	Mono<Demo> findById(@NotNull Long aLong); 

	@ReadOnly
    Flux<Demo> findAll();
}
