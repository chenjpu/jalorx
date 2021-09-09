package io.jalorx.security.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.validation.Validated;

@Introspected
@Validated
@MappedEntity("tpl_user_role_t")
public class UserRoleRelation {
	@EmbeddedId
	private RelationId id;

	private LocalDateTime statTime;
	private LocalDateTime endTime;

	public RelationId getId() {
		return id;
	}

	public void setId(RelationId id) {
		this.id = id;
	}

	public LocalDateTime getStatTime() {
		return statTime;
	}

	public void setStatTime(LocalDateTime statTime) {
		this.statTime = statTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public UserRoleRelation() {
	}

	private UserRoleRelation(RelationId id) {
		this.id = id;
	}

	public static UserRoleRelation valueOf(long userId, long roleId) {
		return new UserRoleRelation(new RelationId(userId, roleId));
	}

	@Embeddable
	public static class RelationId {
		private final long userId;
		private final long roleId;
		private final long dataId = 1;

		public RelationId(long userId, long roleId) {
			this.userId = userId;
			this.roleId = roleId;
		}

		public long getUserId() {
			return userId;
		}

		public long getRoleId() {
			return roleId;
		}

		public long getDataId() {
			return dataId;
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
			return userId == projectId1.userId && roleId == projectId1.roleId && dataId == projectId1.dataId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(userId, roleId, dataId);
		}
	}

}
