/**
 * 
 */
package io.jalorx.lookup.dao;

import java.util.List;

import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.lookup.entity.Lookup;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;


/**
 * @author chenb
 *
 */
@JdbcRepository(dialect = Dialect.MYSQL) 
public interface LookupDao extends BaseRepository<Lookup> {

  List<Lookup> findByParentId(Long parentId);

  List<Lookup> findByGroupCode(String groupCode);

  Lookup getByCodeAndGroupCode(String code, String groupCode);

  List<Lookup> findByCodeInAndGroupCode(String[] codes,String groupCode);

}
