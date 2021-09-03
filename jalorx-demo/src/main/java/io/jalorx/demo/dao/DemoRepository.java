package io.jalorx.demo.dao;

import javax.validation.constraints.NotNull;

import io.jalorx.demo.model.Demo;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.MYSQL) 
public interface DemoRepository extends ReactiveStreamsCrudRepository<Demo, Long> {
    @Override
    Mono<Demo> findById(@NotNull Long aLong); 

    @Override
    Flux<Demo> findAll();
}