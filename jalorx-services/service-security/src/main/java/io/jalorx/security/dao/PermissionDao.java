package io.jalorx.security.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.RowRule;
import io.jalorx.boot.dao.BaseDao;
import io.jalorx.security.entity.PermissionVO;

/**
 * @author chenb
 */
@Mapper
public interface PermissionDao extends BaseDao<PermissionVO> {

  void delPermsByRoleId(long roleId);

  /**
   * 删除指定角色的权限
   * 
   * @param roleIds
   */
  void delPermsByRoleIds(@Param("ids") Long[] roleIds);

  void insertPerms(@Param("roleId") long roleId, @Param("perms") String[] perms);

	Set<Serializable> getPermissionsByRoleId(Serializable roleId);

  Set<Serializable> findPermissionsByUserId(Serializable userId);

	/**
	 * 获取行级规则
	 * 
	 * @param ruleId
	 * @return
	 */
	List<RowRule> getDataRowRule(Serializable ruleId);
}
