package io.jalorx.security.service.impl;

import java.io.Serializable;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.jalorx.boot.annotation.Cache;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.security.dao.GroupDao;
import io.jalorx.security.entity.Group;
import io.jalorx.security.service.GroupService;

/**
 * 组service
 * 
 * @author chenb
 */
@Singleton
@Cache(name = "JalorX:Lookup", expire = 24 * 60 * 60)
public class GroupServiceImpl extends BaseServiceImpl<Group> implements GroupService {

  @Inject
  GroupDao dao;

  @Override
  protected GroupDao getDao() {
    return dao;
  }


  /**
   * 根据组ID获取用户
   */
  @Override
  public Long[] getUsersByGroupId(Long groupId) {
    return getDao().getUsersByGroupId(groupId);
  }

  /**
   * 根据组ID获取用户邮箱
   */
  @Override
  public String[] getUserEmailsByGroupIds(Long[] groupIds) {
    return getDao().getUserEmailsByGroupIds(groupIds);
  }

  /**
   * 组设置用户
   */
  @Override
  public void groupUserSetting(Long groupId, Long[] userIds) {
    getDao().insertGroupUsers(groupId, userIds);
  }

  /**
   * 删除组内用户
   */
  @Override
  public void delGroupUsersByIds(Long groupId, Long[] userIds) {
    getDao().delGroupUsersByIds(groupId, userIds);
  }

  /**
   * 查找组
   */
  @Override
  public Group findGroupByCode(String code) {
    return getDao().findGroupByCode(code);
  }

  /**
   * 查找用户加入的组
   */
  @Override
  public String[] getGroupIdsByUserId(Serializable userId) {
    return getDao().getGroupIdsByUserId(userId);
  }

}
