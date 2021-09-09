package io.jalorx.security.dao;

import io.jalorx.security.entity.UserGroupRelation;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserGroupRelationDao extends GenericRepository<UserGroupRelation, UserGroupRelation.RelationId> {

	Iterable<UserGroupRelation> saveAll(Iterable<UserGroupRelation> entities);

	void deleteByGroupIdAndUserIdIn(Long id, Long[] userIds);

	Long[] findUserIdByGroupIdIn(Long[] groupIds);

	Long[] getGroupIdByUserId(Long userId);
	
	Long[] findUserIdByGroupId(Long groupId);
}
