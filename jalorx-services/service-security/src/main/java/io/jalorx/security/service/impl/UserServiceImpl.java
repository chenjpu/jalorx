package io.jalorx.security.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import io.jalorx.boot.annotation.Cache;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.security.dao.UserDao;
import io.jalorx.security.dao.UserRoleRelationDao;
import io.jalorx.security.entity.User;
import io.jalorx.security.entity.UserRoleRelation;
import io.jalorx.security.service.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * @author chenb
 */
@Singleton
@Cache(name = "JalorX:User", expire = 24 * 60 * 60)
@Named("User.MetaDataClient")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Inject
	UserDao dao;

	@Inject
	UserRoleRelationDao userRoleRelationDao;

	@Override
	protected UserDao getDao() {
		return dao;
	}

	public User findByUserAcount(String username) {
		return getDao().findByAcount(username);
	}

	@Override
	public void userRoleSetting(Long userId, Long[] roleIds) {
		userRoleRelationDao.deleteByUserId(userId);
		if (roleIds.length > 0) {
			List<UserRoleRelation> ugrs = new ArrayList<>(roleIds.length);
			for (Long roleId : roleIds) {
				ugrs.add(UserRoleRelation.valueOf(userId, roleId));
			}
			userRoleRelationDao.saveAll(ugrs);
		}
	}

	@Override
	public List<Long> getRolesByUserId(Long userId) {
		return userRoleRelationDao.findRoleIdByUserId(userId);
	}

	@Override
	public void delUserRolesByIds(long userId, Long[] roleIds) {
		userRoleRelationDao.deleteByUserIdAndRoleIdIn(userId, roleIds);
	}

	@Override
	public void modifyPsw(String pwd, Serializable currentUserId) {
		getDao().update((Long) currentUserId, pwd);
	}

	/**
	 * 修改账户信息
	 */
	@Override
	public void modifyAccount(User user) {
		getDao().update(user);
	}

	@Override
	public List<User.Meta> getDetails(Set<String> idsSet) {
		List<User.Meta> list = new ArrayList<>(idsSet.size());
		for (String ids : idsSet) {
			Long id = Long.valueOf(ids);
			Optional<User> user = dao.findById(id);
			if (user.isPresent()) {
				list.add(user.get().metaof());
			} else {
				list.add(new User.Meta(id));
			}
		}
		return list;
	}
}
