package io.jalorx.security.service;

import java.io.Serializable;

import javax.transaction.Transactional;

import io.jalorx.boot.service.BaseService;
import io.jalorx.security.entity.Group;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * 群组管理服务接口 TODO
 * 
 * @Author: lsn
 * @CreateDate: 2016年6月17日 下午3:57:14
 * @UpdateUser: lsn
 * @UpdateDate: 2016年6月17日 下午3:57:14
 * @UpdateRemark: TODO
 * @Version:v1.0
 */
@Transactional
public interface GroupService extends BaseService<Group> {

  /**
   * 机构群组ID获取该群组已绑定用户信息 TODO @param groupId 组ID @return Long[] @exception
   */
  @ReadOnly
  Long[] getUsersByGroupId(Long groupId);

  /**
   * 机构群组ID获取该群组已绑定用户信息 TODO @param groupId 组ID @return String[] @exception
   */
  @ReadOnly
  String[] getUserEmailsByGroupIds(Long[] groupIds);

  /**
   * 为某群组设置用户绑定信息 TODO @param groupId 组ID @return void @exception
   */
  @Transactional
  void groupUserSetting(Long groupId, Long[] userIds);

  /**
   * 为某群组解除用户绑定信息 TODO @param groupId 组ID @param userIds 用户ID @return void @exception
   */
  @Transactional
  void delGroupUsersByIds(Long groupId, Long[] userIds);

  /**
   * 根据群组编码查询群组信息 TODO @param code 组编码 @return Group @exception
   */
  @ReadOnly
  Group findGroupByCode(String code);

  /**
   * 查询某用户所在的群组信息 TODO @param userId 用户ID @return String[] @exception
   */
  @ReadOnly
  String[] getGroupIdsByUserId(Serializable userId);
}
