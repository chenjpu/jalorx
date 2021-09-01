package io.jalorx.security.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import io.jalorx.boot.annotation.Cache;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.security.dao.UserDao;
import io.jalorx.security.entity.User;
import io.jalorx.security.service.UserService;

/**
 * @author chenb
 */
@Singleton
@Cache(name = "JalorX:User", expire = 24 * 60 * 60)
@Named("User.MetaDataClient")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

  @Inject
  UserDao dao;

  @Override
  protected UserDao getDao() {
    return dao;
  }

  public User findByUserAcount(String username) {
    return getDao().findByUserAcount(username);
  }

  @Override
  public void userRoleSetting(Long userId, Long[] roleIds) {
    dao.delRolesByUserId(userId);
    if (roleIds.length > 0) {
      getDao().insertUserRoles(userId, roleIds);
    }
  }

  @Override
  public Long[] getRolesByUserId(Long userId) {
    return getDao().getRolesByUserId(userId);
  }

  @Override
  public void delUserRolesByIds(long userId, Long[] roleIds) {
    getDao().delUserRolesByIds(userId, roleIds);
  }

  @Override
  public void modifyPsw(String pwd, Serializable currentUserId) {
    getDao().modifyPsw(pwd, (Long) currentUserId);
  }

  /**
   * 修改账户信息
   */
  @Override
  public void modifyAccount(User user) {
    getDao().modifyAccount(user);
  }

  @Override
  public List<User.Meta> getDetails(Set<String> idsSet) {
    List<User.Meta> list = new ArrayList<>(idsSet.size());
    for (String ids : idsSet) {
      Long id = Long.valueOf(ids);
      User user = dao.getById(id);
      if (user != null) {
        list.add(user.metaof());
      } else {
        list.add(new User.Meta(id));
      }
    }
    return list;
  }
}
