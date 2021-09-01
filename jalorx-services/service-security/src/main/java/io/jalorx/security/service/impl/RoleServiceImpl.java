package io.jalorx.security.service.impl;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.model.RuntimeRole;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.security.dao.PermissionDao;
import io.jalorx.security.dao.RoleDao;
import io.jalorx.security.entity.Role;
import io.jalorx.security.service.RoleService;

/**
 * @author chenb
 */
@Singleton
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

  @Inject
  private PermissionDao permissionDao;

  @Inject
  RoleDao dao;

  @Override
  protected RoleDao getDao() {
    return dao;
  }

  // 删除角色，还需要删除对应的权限配置
  @Override
	public void remove(Long[] ids) throws BusinessAccessException {
    if (ArrayUtils.isNotEmpty(ids)) {
      super.remove(ids);
      permissionDao.delPermsByRoleIds(ids);
    }
  }

  // 删除角色，还需要删除对应的权限配置
  @Override
	public void remove(Long id) throws BusinessAccessException {
    super.remove(id);
    permissionDao.delPermsByRoleId(id);
  }

  @Override
  public List<Serializable> findRoleByUserId(Serializable userId) throws BusinessAccessException {
    return getDao().findRoleByUserId(userId);
  }

  @Override
  public Long[] getUsersByRoleId(Long roleId) {
    return getDao().getUsersByRoleId(roleId);
  }

  @Override
  public void userRoleSetting(Long roleId, Long[] userIds) {
    
    if (userIds.length > 0) {
      getDao().insertRoleUsers(roleId, userIds);
    }
  }

  @Override
  public void delRoleUsersByIds(Long roleId, Long[] userIds) {
    getDao().delRoleUsersByIds(roleId, userIds);
  }

  /*
   * 角色删除，批量删除语句拆分，数据库执行最基本的curd
   */
  @Override
  public void deleteRoleAndPermsById(Long id) {
    getDao().deleteById(id);
    getDao().deleteRolePermsById(id);
  }

	@Override
	public List<RuntimeRole> findRuntimeRolesByUserId(Serializable userId) {
		return getDao().findRuntimeRolesByUserId(userId);
	}
}
