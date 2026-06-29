package io.jalorx.attachment.service;

import jakarta.transaction.Transactional;

import io.jalorx.attachment.entity.Attachment;
import io.jalorx.boot.service.BaseService;


@Transactional
public interface AttachmentService extends BaseService<Attachment> {
  
}
