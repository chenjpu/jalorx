package io.jalorx.security.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.jalorx.boot.Account;
import io.jalorx.boot.security.SecurityUserPermissionService;
import io.jalorx.security.dao.PermissionDao;
import io.jalorx.security.dao.RoleDao;
import io.jalorx.security.dao.UserDao;

@Singleton
public class SecurityUserPermissionServiceImpl implements SecurityUserPermissionService {

  @Inject
  private PermissionDao permissionDao;
  @Inject
  private UserDao userDao;
  @Inject
  private RoleDao roleDao;

  @Override
  public Account findByUserAcount(String username) {
    return userDao.findByUserAcount(username);
  }

  @Override
  public List<Serializable> findRoleByUserId(Serializable id) {
    return roleDao.findRoleByUserId(id);
  }

  @Override
  public Set<Serializable> findPermissionsByUserId(Serializable id) {
    return permissionDao.findPermissionsByUserId(id);
  }

}
