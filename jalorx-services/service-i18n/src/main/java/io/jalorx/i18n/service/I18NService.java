package io.jalorx.i18n.service;


import java.util.List;

import javax.transaction.Transactional;

import io.jalorx.boot.Pair;
import io.jalorx.boot.service.BaseService;
import io.jalorx.i18n.entity.I18N;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * 国际化Service.
 * 
 * @author Bruce
 */
@Transactional()
public interface I18NService extends BaseService<I18N> {

  @ReadOnly
  List<Pair> getI18NMessage(String languageCode);

}