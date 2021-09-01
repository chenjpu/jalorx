package io.jalorx.security.service;

import java.io.Serializable;

import javax.transaction.Transactional;

import io.jalorx.boot.json.MetaDataClient;
import io.jalorx.boot.service.BaseService;
import io.jalorx.security.entity.User;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * 用户管理服务接口
 * 
 * @Author: lsn
 * @Version:v1.0
 */
@Transactional
public interface UserService extends BaseService<User>,MetaDataClient<User.Meta,String> {

  /**
   * 根据用户账户查询用户信息，支持只读事务，尝试写入操作将导致失败
   * 
   * @param username 用户账户
   * @return User
   */
  @ReadOnly
  User findByUserAcount(String username);

  /**
   * 为某用户添加角色绑定，支持事务
   * 
   * @param userId 用户ID
   * @param roleIds
   * @return void
   */
  @Transactional
  void userRoleSetting(Long userId, Long[] roleIds);

  /**
   * 获取某用户已绑定角色信息
   * 
   * @param userId 用户ID
   * @return Long[]
   */
  @ReadOnly
  Long[] getRolesByUserId(Long userId);

  /**
   * 为用户解除角色绑定信息
   * 
   * @param userId 用户ID
   * @param roleIds 角色ID
   * @return Long[]
   */
  @Transactional
  void delUserRolesByIds(long userId, Long[] roleIds);

  @Transactional
  void modifyPsw(String pwd, Serializable currentUserId);

  /**
   * 修改账户信息
   * 
   * @param user
   */
  @Transactional
  void modifyAccount(User user);
  
}
