package io.jalorx.security.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.dao.BaseDao;
import io.jalorx.boot.model.RuntimeRole;
import io.jalorx.security.entity.Role;

/**
 * @author chenb
 */
@Mapper
public interface RoleDao extends BaseDao<Role> {

  List<Serializable> findRoleByUserId(Serializable userId);

  Long[] getUsersByRoleId(Long roleId);

  void insertRoleUsers(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);

  void delRoleUsersByIds(@Param("id") Long id, @Param("userIds") Long[] userIds);

  void deleteRolePermsById(@Param("id") Long id);

	List<RuntimeRole> findRuntimeRolesByUserId(Serializable userId);
}
