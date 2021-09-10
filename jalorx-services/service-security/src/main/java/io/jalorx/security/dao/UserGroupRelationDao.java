package io.jalorx.security.dao;

import java.util.List;

import io.jalorx.security.entity.UserGroupRelation;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserGroupRelationDao extends GenericRepository<UserGroupRelation, UserGroupRelation.RelationId> {

	Iterable<UserGroupRelation> saveAll(Iterable<UserGroupRelation> entities);

	void deleteByGroupIdAndUserIdIn(Long id, Long[] userIds);

	List<Long> findUserIdByGroupIdIn(Long[] groupIds);

	List<Long> getGroupIdByUserId(Long userId);
	
	List<Long> findUserIdByGroupId(Long groupId);
}
