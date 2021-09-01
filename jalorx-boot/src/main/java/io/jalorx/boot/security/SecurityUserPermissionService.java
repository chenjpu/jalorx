package io.jalorx.boot.security;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import io.jalorx.boot.Account;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * 需实现的用户接口
 */
public interface SecurityUserPermissionService {
	/**
	 * 获取用户信息
	 * 
	 * @param username
	 * @return
	 */
	@ReadOnly
	Account findByUserAcount(String username);

	/**
	 * 获取角色信息
	 * 
	 * @param id
	 * @return
	 */
	@ReadOnly
	List<Serializable> findRoleByUserId(Serializable id);

	/**
	 * 获取权限信息
	 * 
	 * @param id
	 * @return
	 */
	@ReadOnly
	Set<Serializable> findPermissionsByUserId(Serializable id);
}
