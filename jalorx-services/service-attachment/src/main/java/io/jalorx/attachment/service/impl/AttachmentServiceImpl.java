package io.jalorx.attachment.service.impl;

import io.jalorx.attachment.dao.AttachmentDao;
import io.jalorx.attachment.entity.Attachment;
import io.jalorx.attachment.service.AttachmentService;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * @author chenb
 */
@Singleton
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment>
    implements
      AttachmentService {

  @Inject
  AttachmentDao dao;

  @Override
  protected AttachmentDao getDao() {
    return dao;
  }


}
