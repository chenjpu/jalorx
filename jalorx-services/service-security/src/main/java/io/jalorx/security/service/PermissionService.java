package io.jalorx.security.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import io.jalorx.boot.RowRule;
import io.jalorx.boot.service.BaseService;
import io.jalorx.security.entity.PermissionVO;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * @author chenb
 */
@Transactional
public interface PermissionService extends BaseService<PermissionVO> {

  @Transactional
  void updatePermissions(long roleId, String[] perms);

  @ReadOnly
	Set<Serializable> getPermissionsByRoleId(Serializable roleId);

  @ReadOnly
  Set<Serializable> findPermissionsByUserId(Serializable userId);

	@ReadOnly
	List<RowRule> findDataRowRulesById(Serializable ruleId);
}
