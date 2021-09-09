package io.jalorx.security.entity;

import java.util.Objects;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.validation.Validated;

@Introspected
@Validated
@MappedEntity("tpl_user_group_t")
public class UserGroupRelation {
	@EmbeddedId
	private RelationId id;

	public RelationId getId() {
		return id;
	}
	public void setId(RelationId id) {
		this.id = id;
	}
	
	public UserGroupRelation() {}
	
	private  UserGroupRelation(RelationId id) {
		this.id = id;
	}
	
	public static UserGroupRelation valueOf(long userId, long groupId) {
		return new UserGroupRelation(new RelationId(userId, groupId));
	}
	
	
	@Embeddable
	public static class RelationId {
		@MappedProperty("user_id")
		private final long userId;
		@MappedProperty("group_id")
		private final long groupId;

		public RelationId(long userId, long groupId) {
			this.userId = userId;
			this.groupId = groupId;
		}

		public long getUserId() {
			return userId;
		}

		public long getGroupId() {
			return groupId;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			RelationId projectId1 = (RelationId) o;
			return userId == projectId1.userId && groupId == projectId1.groupId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(userId, groupId);
		}
	}
	
}
