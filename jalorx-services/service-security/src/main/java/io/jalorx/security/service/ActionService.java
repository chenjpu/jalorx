package io.jalorx.security.service;

import jakarta.transaction.Transactional;

import io.jalorx.boot.service.BaseService;
import io.jalorx.security.entity.Action;

@Transactional
public interface ActionService extends BaseService<Action> {
  
}
