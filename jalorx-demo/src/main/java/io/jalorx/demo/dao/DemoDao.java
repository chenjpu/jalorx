package io.jalorx.demo.dao;

import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.demo.model.Demo;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;

@R2dbcRepository(dialect = Dialect.MYSQL) 
public interface DemoDao extends BaseRepository<Demo> {

}
