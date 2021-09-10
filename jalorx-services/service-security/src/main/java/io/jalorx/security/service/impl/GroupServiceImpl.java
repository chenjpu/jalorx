package io.jalorx.security.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.jalorx.boot.annotation.Cache;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.security.dao.GroupDao;
import io.jalorx.security.dao.UserDao;
import io.jalorx.security.dao.UserGroupRelationDao;
import io.jalorx.security.entity.Group;
import io.jalorx.security.entity.UserGroupRelation;
import io.jalorx.security.service.GroupService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

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
	@Inject
	UserGroupRelationDao userGroupRelationDao;
	
	@Inject
	UserDao userDao;

	@Override
	protected GroupDao getDao() {
		return dao;
	}

	/**
	 * 根据组ID获取用户
	 */
	@Override
	public Long[] getUsersByGroupId(Long groupId) {
		return userGroupRelationDao.findUserIdByGroupId(groupId);
	}

	/**
	 * 根据组ID获取用户邮箱
	 */
	@Override
	public String[] getUserEmailsByGroupIds(Long[] groupIds) {
		return userDao.findEmailByIdIn(userGroupRelationDao.findUserIdByGroupIdIn(groupIds));
	}

	/**
	 * 组设置用户
	 */
	@Override
	public void groupUserSetting(Long groupId, Long[] userIds) {
		List<UserGroupRelation> ugrs = new ArrayList<>(userIds.length);
		for (Long userId : userIds) {
			ugrs.add(UserGroupRelation.valueOf(userId, groupId));
		}
		userGroupRelationDao.saveAll(ugrs);
	}

	/**
	 * 删除组内用户
	 */
	@Override
	public void delGroupUsersByIds(Long groupId, Long[] userIds) {
		userGroupRelationDao.deleteByGroupIdAndUserIdIn(groupId, userIds);
	}

	/**
	 * 查找组
	 */
	@Override
	public Group findGroupByCode(String code) {
		return getDao().findByCode(code);
	}

	/**
	 * 查找用户加入的组
	 */
	@Override
	public Long[] getGroupIdsByUserId(Serializable userId) {
		return userGroupRelationDao.getGroupIdByUserId((Long)userId);
	}

}
