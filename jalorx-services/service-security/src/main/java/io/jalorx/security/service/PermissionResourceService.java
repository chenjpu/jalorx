package io.jalorx.security.service;

import java.util.List;

import javax.transaction.Transactional;

import io.jalorx.boot.entity.PermissionOperation;
import io.jalorx.boot.entity.PermissionResource;
import io.jalorx.boot.service.BaseService;
import io.micronaut.transaction.annotation.ReadOnly;

@Transactional
public interface PermissionResourceService extends BaseService<PermissionResource> {

  @ReadOnly
  public List<PermissionOperation> getAllPermission();
}
