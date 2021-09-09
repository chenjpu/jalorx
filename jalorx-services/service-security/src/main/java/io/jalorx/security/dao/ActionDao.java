package io.jalorx.security.dao;

import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.security.entity.Action;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

/**
 * @author lll
 */
@JdbcRepository(dialect = Dialect.MYSQL) 
public interface ActionDao extends BaseRepository<Action> {
  
}
