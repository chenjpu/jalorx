package io.jalorx.security.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.jalorx.boot.Account;
import io.jalorx.boot.security.SecurityUserPermissionService;
import io.jalorx.security.dao.RolePermsRelationDao;
import io.jalorx.security.dao.UserDao;
import io.jalorx.security.dao.UserRoleRelationDao;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class SecurityUserPermissionServiceImpl implements SecurityUserPermissionService {

  @Inject
  private UserDao userDao;
  
  @Inject
  private UserRoleRelationDao userRoleRelationDao;
  
  @Inject
  RolePermsRelationDao rolePermsRelationDao;

  @Override
  public Account findByUserAcount(String username) {
    return userDao.findByAcount(username);
  }

  @Override
  public List<Serializable> findRoleByUserId(Serializable id) {
	  List<Long> roleIds = Arrays.asList(userRoleRelationDao.findRoleIdByUserId((Long)id));
	  return new ArrayList<>(roleIds);
  }
	  

  @Override
  public Set<Serializable> findPermissionsByUserId(Serializable id) {
	  return new HashSet<>(rolePermsRelationDao.findCodeByUserId((Long)id));
  }

}
