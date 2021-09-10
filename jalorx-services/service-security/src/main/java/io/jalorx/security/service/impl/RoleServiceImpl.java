package io.jalorx.security.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.model.RuntimeRole;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.security.dao.RoleDao;
import io.jalorx.security.dao.RolePermsRelationDao;
import io.jalorx.security.dao.UserRoleRelationDao;
import io.jalorx.security.entity.Role;
import io.jalorx.security.entity.UserRoleRelation;
import io.jalorx.security.service.RoleService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * @author chenb
 */
@Singleton
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

  @Inject
  RoleDao dao;
  
  @Inject
  UserRoleRelationDao userRoleRelationDao;
  
  @Inject
  RolePermsRelationDao rolePermsRelationDao;

  @Override
  protected RoleDao getDao() {
    return dao;
  }

  // 删除角色，还需要删除对应的权限配置
  @Override
	public void remove(Long[] ids) throws BusinessAccessException {
    if (ArrayUtils.isNotEmpty(ids)) {
      super.remove(ids);
      rolePermsRelationDao.deleteByRoleIdIn(ids);
    }
  }

  // 删除角色，还需要删除对应的权限配置
  @Override
	public void remove(Long id) throws BusinessAccessException {
    super.remove(id);
    rolePermsRelationDao.deleteByRoleId(id);
  }

  @Override
  public List<Serializable> findRoleByUserId(Serializable userId) throws BusinessAccessException {
	  List<Long> roleIds = Arrays.asList(userRoleRelationDao.findRoleIdByUserId((Long)userId));
	  return new ArrayList<>(roleIds);
  }

  @Override
  public Long[] getUsersByRoleId(Long roleId) {
    return userRoleRelationDao.getUserIdByRoleId(roleId);
  }

  @Override
  public void userRoleSetting(Long roleId, Long[] userIds) {
    
    if (userIds.length > 0) {
    	List<UserRoleRelation> ugrs = new ArrayList<>(userIds.length);
		for (Long userId : userIds) {
			ugrs.add(UserRoleRelation.valueOf(userId, roleId));
		}
		userRoleRelationDao.saveAll(ugrs);
    }
  }

  @Override
  public void delRoleUsersByIds(Long roleId, Long[] userIds) {
	  userRoleRelationDao.deleteByRoleIdAndUserIdIn(roleId, userIds);
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
		return userRoleRelationDao.findByUserId((Long)userId);
	}
}
