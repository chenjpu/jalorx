package io.jalorx.security.dao;

import java.io.Serializable;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.dao.BaseDao;
import io.jalorx.security.entity.Group;

/**
 * @author chenb
 */
@Mapper
public interface GroupDao extends BaseDao<Group> {

  Long[] getUsersByGroupId(Long groupId);

  String[] getUserEmailsByGroupIds(@Param("groupIds") Long[] groupIds);

  void insertGroupUsers(@Param("groupId") Long groupId, @Param("userIds") Long[] userIds);

  void delGroupUsersByIds(@Param("id") Long id, @Param("userIds") Long[] userIds);

  String[] getGroupCodesByUserId(Serializable userId);

  Group findGroupByCode(String code);

  String[] getGroupIdsByUserId(Serializable userId);


}
