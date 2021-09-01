package io.jalorx.attachment.dao;

import org.apache.ibatis.annotations.Mapper;

import io.jalorx.attachment.entity.Attachment;
import io.jalorx.boot.dao.BaseDao;


@Mapper
public interface AttachmentDao extends BaseDao<Attachment> {
  
  
}
