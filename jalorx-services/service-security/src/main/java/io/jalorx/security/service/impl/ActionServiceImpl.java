package io.jalorx.security.service.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.security.dao.ActionDao;
import io.jalorx.security.entity.Action;
import io.jalorx.security.service.ActionService;

/**
 * 组service
 * 
 * @author chenb
 */
@Singleton
public class ActionServiceImpl extends BaseServiceImpl<Action> implements ActionService {

  @Inject
  ActionDao dao;

  @Override
  protected ActionDao getDao() {
    return dao;
  }


}
