package io.jalorx.simple.dao;

import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.simple.model.Demo;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

@JdbcRepository(dialect = Dialect.MYSQL) 
public interface DemoDao extends BaseRepository<Demo> {

}
