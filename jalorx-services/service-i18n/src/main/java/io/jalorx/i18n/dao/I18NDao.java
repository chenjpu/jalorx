package io.jalorx.i18n.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.Pair;
import io.jalorx.boot.dao.BaseDao;
import io.jalorx.i18n.entity.I18N;

/**
 * 国际化DAO.
 * 
 * @author Bruce
 */
@Mapper
public interface I18NDao extends BaseDao<I18N> {

  I18N getLastUpdateTime(@Param("languageCode") String languageCode);

  List<Pair> getI18NMessage(String languageCode);

}
