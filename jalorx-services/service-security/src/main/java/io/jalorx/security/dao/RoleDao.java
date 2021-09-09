package io.jalorx.security.dao;

import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.security.entity.Role;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

/**
 * @author chenb
 */
@JdbcRepository(dialect = Dialect.MYSQL)
public interface RoleDao extends BaseRepository<Role> {

	@Query("delete from tpl_app_role_perms_t where role_id=:id")
	void deleteRolePermsById(@Param("id") Long id);

}
