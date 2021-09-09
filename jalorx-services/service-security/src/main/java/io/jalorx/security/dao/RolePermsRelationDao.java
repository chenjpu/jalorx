package io.jalorx.security.dao;

import java.util.List;

import io.jalorx.security.entity.RolePermsRelation;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface RolePermsRelationDao extends GenericRepository<RolePermsRelation, RolePermsRelation.RelationId> {

	Iterable<RolePermsRelation> saveAll(Iterable<RolePermsRelation> entities);
	
	void deleteByRoleId(long roleId);
	
	/**
	 * 删除指定角色的权限
	 * 
	 * @param roleIds
	 */
	void deleteByRoleIdIn(Long[] roleIds);
	
	
	List<String> findCodeByRoleId(Long roleId);

	@Query("SELECT DISTINC (T. CODE) FROM TPL_APP_ROLE_PERMS_T T "
			+ "WHERE T.ROLE_ID IN ("
			+ "SELECT T.ROLE_ID FROM tpl_user_role_t T, tpl_app_role_t R WHERE"
			+ "T.ROLE_ID = R.ID AND R.STATUS = 1 AND T.USER_ID = :userId)")
	List<String> findCodeByUserId(Long userId);

}
