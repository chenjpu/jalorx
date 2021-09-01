package io.jalorx.security.service;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.model.RuntimeRole;
import io.jalorx.boot.service.BaseService;
import io.jalorx.security.entity.Role;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * 角色管理服务接口
 * 
 * @Author: lsn
 * @CreateDate: 2016年6月17日 下午4:11:18
 * @UpdateUser: lsn
 * @UpdateDate: 2016年6月17日 下午4:11:18
 * @Version:v1.0
 */
public interface RoleService extends BaseService<Role> {

  /**
   * 获取某用户已绑定角色信息 @param userId 用户ID @return List<Serializable> @exception
   */
  @ReadOnly
  List<Serializable> findRoleByUserId(Serializable userId) throws BusinessAccessException;

	/**
	 * 获取用户运行时角色
	 * 
	 * @param userId
	 * @return
	 */
	@ReadOnly
	List<RuntimeRole> findRuntimeRolesByUserId(Serializable userId);

  /**
   * 获取某角色已绑定用户信息 @param roleId 角色ID @return Long[] @exception
   */
  @ReadOnly
  Long[] getUsersByRoleId(Long roleId);

  /**
   * 为某角色设置绑定用户 @param roleId 角色ID @param userIds 用户ID @return @exception
   */
  @Transactional
  void userRoleSetting(Long roleId, Long[] userIds);

  /**
   * 为某角色解除用户绑定 @param roleId 角色ID @param userIds 用户ID @return void @exception
   */
  @Transactional
  void delRoleUsersByIds(Long roleId, Long[] userIds);

  /**
   * @Description: 角色删除，批量删除语句拆分，数据库执行最基本的curd
   * @Author CHENTAO
   * @Date 2016年12月2日 上午10:17:56
   * @param id
   */
  @Transactional
  void deleteRoleAndPermsById(@Param("id") Long id);

}
