package io.jalorx.attachment.service;

import javax.transaction.Transactional;

import io.jalorx.attachment.entity.Attachment;
import io.jalorx.boot.service.BaseService;


@Transactional
public interface AttachmentService extends BaseService<Attachment> {
  
}
