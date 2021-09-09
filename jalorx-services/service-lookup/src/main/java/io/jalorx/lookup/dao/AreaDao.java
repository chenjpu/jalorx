package io.jalorx.lookup.dao;


import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.lookup.entity.Area;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

/**
 * @author chenb
 *
 */
@JdbcRepository(dialect = Dialect.MYSQL) 
public interface AreaDao extends BaseRepository<Area> {

}
