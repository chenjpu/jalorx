package io.jalorx.security.dao;

import io.jalorx.boot.entity.PermissionOperation;
import io.jalorx.boot.repository.BaseRepository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface PermissionOperationDao extends BaseRepository<PermissionOperation> {
  /**
   * 批量删除记录
   * 
   * @param appCode
   * @param rids resources值集合
   */
  //void deleteByRescode(@Param("appCode") String appCode, @Param("rids") List<String> rids);
}
