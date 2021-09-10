package io.jalorx.security.dao;

import java.util.List;

import io.jalorx.boot.model.RuntimeRole;
import io.jalorx.security.entity.UserRoleRelation;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserRoleRelationDao extends GenericRepository<UserRoleRelation, UserRoleRelation.RelationId> {

	Iterable<UserRoleRelation> saveAll(Iterable<UserRoleRelation> entities);

	void deleteByUserIdAndRoleIdIn(Long userId, Long[] roleIds);

	List<Long> findRoleIdByUserId(Long userId);

	void deleteByUserId(long id);
	
	List<Long> getUserIdByRoleId(Long roleId);
	
	void deleteByRoleIdAndUserIdIn(Long id, Long[] userIds);

	@Query("select"
			+ "		 T.ROLE_ID,"
			+ "		 T.DATA_ID"
			+ "		from TPL_USER_ROLE_T T,TPL_APP_DATA_ROLE_T DR,TPL_APP_ROLE_T R\n"
			+ "		where "
			+ "		 T.ROLE_ID=R.ID "
			+ "		 and T.DATA_ID=DR.ID "
			+ "		 and R.STATUS=1 "
			+ "		 and DR.STATUS=1 "
			+ "		 and T.USER_ID=:userId"
			+ "		 and (T.START_TIME is null or T.START_TIME >= now())\n"
			+ "		 and (T.END_TIME is null or T.END_TIME <= now())")
	List<RuntimeRole> findByUserId(Long userId);
}
