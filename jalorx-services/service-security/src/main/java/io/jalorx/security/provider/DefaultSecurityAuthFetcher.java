package io.jalorx.security.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.jalorx.boot.RowRule;
import io.jalorx.boot.model.RuntimeRole;
import io.jalorx.boot.security.provider.AuthFetcher;
import io.jalorx.security.service.PermissionService;
import io.jalorx.security.service.RoleService;
import io.micronaut.context.annotation.Replaces;

@Singleton
@Replaces(AuthFetcher.class)
public class DefaultSecurityAuthFetcher implements AuthFetcher {

	@Inject
	PermissionService permissionService;

	@Inject
	RoleService roleService;

	@Override
	public Collection<String> findAuthoritiesByRoleID(Serializable roleID) {
		LOG.debug("Find authorities by RoleID={}", roleID);
		Set<Serializable> perms = permissionService.getPermissionsByRoleId(roleID);
		return convert(String::valueOf, perms);
	}

	@Override
	public Collection<RuntimeRole> findRuntimeRolesByUserID(Serializable userID) {
		LOG.debug("Find roles by UserID={}", userID);
		List<RuntimeRole> roles = roleService.findRuntimeRolesByUserId(userID);
		return roles;
	}

	@Override
	public Collection<RowRule> findDataRowRuleByRuleID(Serializable ruleID) {
		LOG.debug("Find RowRule by RuleID={}", ruleID);
		List<RowRule> rowRules = permissionService.findDataRowRulesById(ruleID);
		return rowRules;
	}

	private <T> Collection<T> convert(Function<Object, T> f, Collection<?> c) {
		Collection<T> result = new ArrayList<T>(c.size());
		for (Object t : c) {
			result.add(f.apply(t));
		}
		return result;
	}

}
