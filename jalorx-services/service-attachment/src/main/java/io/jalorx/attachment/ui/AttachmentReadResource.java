package io.jalorx.attachment.ui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.validation.constraints.NotNull;

import io.jalorx.attachment.entity.Attachment;
import io.jalorx.attachment.service.AttachmentService;
import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseReadResource;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.server.types.files.SystemFile;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/attachment")
@Resource(code = 10104, desc = "Attachment Resource")
@Operation.Read
@Validated
@Tag(name = "attachment")
public class AttachmentReadResource extends BaseReadResource<Attachment> {


  @Inject
  AttachmentService service;

  @Override
  protected BaseService<Attachment> getService() {
    return service;
  }

  /**
   * 附件下载
   * 
   * @param id
   * @throws UnsupportedEncodingException
   */
  @Get("/download/{id}")
  public SystemFile download(@PathVariable("id") @NotNull Long id)
      throws UnsupportedEncodingException, BusinessAccessException {
    Attachment attachment = service.get(id);
    String filePath = attachment.getFilePath();
    String fileName = attachment.getFileName();
    File file = new File(filePath);
    return new SystemFile(file, MediaType.forFilename(fileName))
        .attach(URLEncoder.encode(fileName, "UTF-8"));
  }
}
