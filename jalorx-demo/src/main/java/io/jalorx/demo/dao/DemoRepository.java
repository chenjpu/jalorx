package io.jalorx.demo.dao;

import io.jalorx.demo.model.Demo;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.r2dbc.repository.ReactorCrudRepository;
import reactor.core.publisher.Flux;

@R2dbcRepository(dialect = Dialect.MYSQL) 
public interface DemoRepository extends ReactorCrudRepository<Demo, Long> {

    Flux<Demo> findByAgeGreaterThan(int age);
    
}