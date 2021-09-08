package io.jalorx.demo.dao;

import java.util.Optional;

import io.jalorx.boot.sql.SelectProvider;
import io.jalorx.demo.model.Demo;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;

@JdbcRepository(dialect = Dialect.MYSQL) 
public interface DemoRepository2 extends GenericRepository<Demo, Long>{
	

	 Optional<Demo> findOne(SelectProvider<Demo> sqlProvider);

	 Iterable<Demo> findAll(SelectProvider<Demo> sqlProvider);
	 
	 Iterable<Demo> findByAgeGreaterThan(int age);
    
}