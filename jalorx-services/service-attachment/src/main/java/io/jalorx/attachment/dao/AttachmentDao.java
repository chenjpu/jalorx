package io.jalorx.attachment.dao;

import io.jalorx.attachment.entity.Attachment;
import io.jalorx.boot.repository.BaseRepository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;


@JdbcRepository(dialect = Dialect.MYSQL) 
public interface AttachmentDao extends BaseRepository<Attachment> {
  
  
}
