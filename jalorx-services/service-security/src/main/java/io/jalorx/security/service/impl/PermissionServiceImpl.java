
package io.jalorx.security.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.jalorx.boot.RowRule;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.security.dao.PermissionDao;
import io.jalorx.security.entity.PermissionVO;
import io.jalorx.security.service.PermissionService;

/**
 * @author chenb
 */
@Singleton
public class PermissionServiceImpl extends BaseServiceImpl<PermissionVO>
    implements
      PermissionService {
  @Inject
  PermissionDao dao;

  @Override
  protected PermissionDao getDao() {
    return dao;
  }

  /**
   * 根据角色修改权限.
   */
  @Override
	public void updatePermissions(long roleId, String[] perms) {
    getDao().delPermsByRoleId(roleId);
    if (perms.length > 0) {
      getDao().insertPerms(roleId, perms);
    }
  }

  /**
   * 根据角色获取权限.
   */
	@Override
	public Set<Serializable> getPermissionsByRoleId(Serializable roleId) {
    return getDao().getPermissionsByRoleId(roleId);
  }

  /**
   * 根据用户获取权限.
   */
  @Override
  public Set<Serializable> findPermissionsByUserId(Serializable userId) {
    return getDao().findPermissionsByUserId(userId);
  }

	@Override
	public List<RowRule> findDataRowRulesById(Serializable ruleId) {
		return getDao().getDataRowRule(ruleId);
	}
}
