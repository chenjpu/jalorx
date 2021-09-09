package io.jalorx.i18n.dao;

import java.util.List;

import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.i18n.entity.I18N;
import io.jalorx.i18n.entity.I18NDTO;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

/**
 * 国际化DAO.
 * 
 * @author Bruce
 */
@JdbcRepository(dialect = Dialect.MYSQL) 
public interface I18NDao extends BaseRepository<I18N> {

  I18N findByLanguageCodeOrderByLastUpdateDate(String languageCode);

  List<I18NDTO> findByLanguageCode(String languageCode);

}
