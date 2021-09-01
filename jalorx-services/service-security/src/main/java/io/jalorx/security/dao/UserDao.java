package io.jalorx.security.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.dao.BaseDao;
import io.jalorx.security.entity.User;

/**
 * @author chenb
 */
@Mapper
public interface UserDao extends BaseDao<User> {

  User findByUserAcount(String username);

  void insertUserRoles(@Param("userId") Long userId, @Param("roleIds") Long[] roleIds);

  void delRolesByUserId(long id);

  void delUserRolesByIds(@Param("id") long id, @Param("roleIds") Long[] roleIds);

  Long[] getRolesByUserId(@Param("userId") Long userId);

  void modifyPsw(@Param("password") String password, @Param("currentUserId") Long currentUserId);

  void modifyAccount(User user);
}
