package io.jalorx.boot.security.provider;

import java.io.Serializable;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.RowRule;
import io.jalorx.boot.model.RuntimeRole;
import io.micronaut.context.annotation.DefaultImplementation;

/**
 * Responsible for finding granted authorities for a given user.
 * 
 * @author chenjpu
 * @since 2.0
 */

@DefaultImplementation(DefaultAuthFetcher.class)
public interface AuthFetcher {

	Logger LOG = LoggerFactory.getLogger(AuthFetcher.class);

	/**
	 * 返回当前用户的所有角色的权限集合
	 * 
	 * @param roleID
	 * @return
	 */
	Collection<String> findAuthoritiesByRoleID(Serializable roleID);

	/**
	 * 返回当前用户的所有的角色集合
	 * 
	 * roleID-dataID
	 * 
	 * @param userID
	 * @return
	 */
	Collection<RuntimeRole> findRuntimeRolesByUserID(Serializable userID);

	/**
	 * 获取指定id的行级数据规则
	 * 
	 * @param ruleID
	 * @return
	 */
	Collection<RowRule> findDataRowRuleByRuleID(Serializable ruleID);

}
