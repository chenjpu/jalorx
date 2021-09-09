package io.jalorx.security.entity;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.validation.Validated;

@Introspected
@Validated
@MappedEntity("tpl_app_role_perms_t")
public class RolePermsRelation {
	@EmbeddedId
	private RelationId id;

	public RelationId getId() {
		return id;
	}

	public void setId(RelationId id) {
		this.id = id;
	}


	public RolePermsRelation() {
	}

	private RolePermsRelation(RelationId id) {
		this.id = id;
	}

	public static RolePermsRelation valueOf(long roleId,String code) {
		return new RolePermsRelation(new RelationId(roleId,code));
	}

	@Embeddable
	public static class RelationId {
		@MappedProperty("code")
		private final String code;
		@MappedProperty("role_id")
		private final long roleId;

		public RelationId(long roleId,String code) {
			this.roleId = roleId;
			this.code = code;
		}


		public long getRoleId() {
			return roleId;
		}


		public String getCode() {
			return code;
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
			return roleId == projectId1.roleId && StringUtils.equals(code, projectId1.code);
		}

		@Override
		public int hashCode() {
			return Objects.hash(roleId, code);
		}
	}

}
