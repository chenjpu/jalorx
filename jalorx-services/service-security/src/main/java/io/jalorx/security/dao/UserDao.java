package io.jalorx.security.dao;

import java.util.List;

import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.security.entity.User;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

/**
 * @author chenb
 */
@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserDao extends BaseRepository<User> {

  User findByAcount(String username);

  void update(@Id Long id, String password);

  String[] findEmailByIdIn(List<Long> Ids);
}
