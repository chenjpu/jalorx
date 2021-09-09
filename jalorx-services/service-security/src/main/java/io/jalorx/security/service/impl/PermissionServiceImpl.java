
package io.jalorx.security.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.jalorx.boot.RowRule;
import io.jalorx.security.dao.RolePermsRelationDao;
import io.jalorx.security.entity.RolePermsRelation;
import io.jalorx.security.service.PermissionService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * @author chenb
 */
@Singleton
public class PermissionServiceImpl  implements PermissionService {

	@Inject
	RolePermsRelationDao rolePermsRelationDao;

	/**
	 * 根据角色修改权限.
	 */
	@Override
	public void updatePermissions(long roleId, String[] perms) {
		rolePermsRelationDao.deleteByRoleId(roleId);
		if (perms.length > 0) {
			List<RolePermsRelation> ugrs = new ArrayList<>(perms.length);
			for (String code : perms) {
				ugrs.add(RolePermsRelation.valueOf(roleId, code));
			}
			rolePermsRelationDao.saveAll(ugrs);
		}
	}

	/**
	 * 根据角色获取权限.
	 */
	@Override
	public Set<Serializable> getPermissionsByRoleId(Serializable roleId) {
		return new HashSet<>(rolePermsRelationDao.findCodeByRoleId((Long)roleId));
	}

	/**
	 * 根据用户获取权限.
	 */
	@Override
	public Set<Serializable> findPermissionsByUserId(Serializable userId) {
		return new HashSet<>(rolePermsRelationDao.findCodeByUserId((Long)userId));
	}

	@Override
	public List<RowRule> findDataRowRulesById(Serializable ruleId) {
		throw new UnsupportedOperationException();
		//return getDao().getDataRowRule(ruleId);
	}
}
