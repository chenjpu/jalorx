package io.jalorx.security.dao;

import java.io.Serializable;

import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.security.entity.Group;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

/**
 * @author chenb
 */
@JdbcRepository(dialect = Dialect.MYSQL)
public interface GroupDao extends BaseRepository<Group> {

  @Query("select distinct g.code as group_code from tpl_user_group_t t join tpl_app_group_t g on (t.group_id = g.id) where t.user_id = :userId")
  String[] findGroupCodeByUserId(Serializable userId);

  Group findByCode(String code);
}
