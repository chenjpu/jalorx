package io.jalorx.attachment.ui;

import io.jalorx.attachment.entity.Attachment;
import io.jalorx.attachment.service.AttachmentService;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseDeleteResource;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/attachment")
@Resource(code=10104,desc = "Attachment Resource")
@Operation.Delete
@Validated
@Tag(name = "attachment")
public class AttachmentDeleteResource extends BaseDeleteResource<Attachment> {


  @Inject
  AttachmentService service;

  @Override
  protected BaseService<Attachment> getService() {
    return service;
  }

}
