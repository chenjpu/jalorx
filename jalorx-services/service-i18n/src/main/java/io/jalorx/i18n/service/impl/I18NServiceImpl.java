package io.jalorx.i18n.service.impl;


import java.util.List;

import io.jalorx.boot.Pair;
import io.jalorx.boot.annotation.Cache;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.i18n.dao.I18NDao;
import io.jalorx.i18n.entity.I18N;
import io.jalorx.i18n.service.I18NService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * 国际化Service.
 * 
 * @author Bruce
 */
@Singleton
@Cache(name = "JalorX:I18N")
public class I18NServiceImpl extends BaseServiceImpl<I18N> implements I18NService {

  @Inject
  private I18NDao dao;

  protected I18NDao getDao() {
    return dao;
  }

  /**
   * 获取最后更新时间，用于判断前端库是否需要更新.
   */
  @Override
  public I18N getLastUpdateTime(String languageCode) {
    return dao.getLastUpdateTime(languageCode);
  }

  public List<Pair> getI18NMessage(String languageCode) {
    return dao.getI18NMessage(languageCode);
  }

  ////

}
