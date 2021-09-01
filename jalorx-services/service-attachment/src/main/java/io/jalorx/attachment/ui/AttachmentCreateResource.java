package io.jalorx.attachment.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.reactivestreams.Publisher;

import io.jalorx.attachment.entity.Attachment;
import io.jalorx.attachment.service.AttachmentService;
import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;

@Controller("/attachment")
@Resource(code = 10104, desc = "Attachment Resource")
@Operation.Create
@Validated
@Tag(name = "attachment")
public class AttachmentCreateResource {


  @Inject
  AttachmentService service;

  @Value("${uploadUrl}")
  protected String uploadUrl;


  /**
   * 附件上传
   * 
   * @param file
   * @param batchNo
   */
  @Post(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA)
  public Publisher<HttpResponse<?>> upload(@Body("file") StreamingFileUpload file, @Body("batchNo") String batchNo)
      throws IOException, BusinessAccessException {
    // 文件保存路径
    Path dir = Paths.get(uploadUrl, DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
    File dirFile = dir.toFile();

    // 如果文件夹不存在，则新建文件夹
    if (!dirFile.exists()) {
      dirFile.mkdirs();
    }

    String uuid = UUID.randomUUID().toString();
    // 文件重命名，防止重复
    Path filePath = dir.resolve(uuid);
    // 文件对象
    Publisher<Boolean> uploadPublisher = file.transferTo(filePath.toFile());
    return Flux.from(uploadPublisher).map(success -> {
      if (success) {
        Attachment at = new Attachment();
        at.setFileName(file.getFilename());
        at.setFilePath(filePath.toString());
        at.setBatchNo(StringUtils.isEmpty(batchNo) ? uuid : batchNo);
        at.setFileSize((int) file.getSize());
        service.save(at);
        Long fileId = at.getId();
        return HttpResponse.ok(fileId);
      } else {
        return HttpResponse.status(HttpStatus.CONFLICT).body("Upload Failed");
      }
    });
  }
}
