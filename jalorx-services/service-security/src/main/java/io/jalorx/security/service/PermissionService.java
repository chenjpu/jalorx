package io.jalorx.security.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import io.jalorx.boot.RowRule;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * @author chenb
 */
@Transactional
public interface PermissionService {

	@Transactional
	void updatePermissions(long roleId, String[] perms);

	@ReadOnly
	Set<Serializable> getPermissionsByRoleId(Serializable roleId);

	@ReadOnly
	Set<Serializable> findPermissionsByUserId(Serializable userId);

	@ReadOnly
	List<RowRule> findDataRowRulesById(Serializable ruleId);
}
