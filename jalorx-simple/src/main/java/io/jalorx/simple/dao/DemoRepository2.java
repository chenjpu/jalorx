package io.jalorx.simple.dao;

import io.jalorx.simple.model.Demo;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;

@JdbcRepository(dialect = Dialect.MYSQL) 
public interface DemoRepository2 extends GenericRepository<Demo, Long>{
	

	 //Optional<Demo> findOne(SelectProvider sqlProvider);

	 //Page<Demo> findAll(SelectProvider sqlProvider,Pageable pageable);
	 
	 Iterable<Demo> findByAgeGreaterThan(int age);
    
}