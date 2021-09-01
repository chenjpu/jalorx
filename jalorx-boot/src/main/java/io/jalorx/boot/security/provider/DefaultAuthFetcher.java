package io.jalorx.boot.security.provider;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import io.jalorx.boot.RowRule;
import io.jalorx.boot.model.RuntimeRole;
import jakarta.inject.Singleton;

@Singleton
public class DefaultAuthFetcher implements AuthFetcher {

	@Override
	public Collection<String> findAuthoritiesByRoleID(Serializable roleID) {
		LOG.debug("Find empty authorities by RoleID={}", roleID);
		return Collections.emptyList();
	}

	@Override
	public Collection<RuntimeRole> findRuntimeRolesByUserID(Serializable userID) {
		LOG.debug("Find empty roles by UserID={}", userID);
		return Collections.emptyList();
	}

	@Override
	public Collection<RowRule> findDataRowRuleByRuleID(Serializable ruleID) {
		LOG.debug("Find empty RowRule by RuleID={}", ruleID);
		return Collections.emptyList();
	}
}
