package io.jalorx.lookup.service.impl;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.lookup.dao.AreaDao;
import io.jalorx.lookup.entity.Area;
import io.jalorx.lookup.service.AreaService;


/**
 * @author chenb
 *
 */
@Singleton
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService {

  @Inject
  AreaDao dao;

  @Override
  protected AreaDao getDao() {
    return dao;
  }
}
