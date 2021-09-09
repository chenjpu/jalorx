package io.jalorx.attachment.entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.validation.Validated;


@Introspected
@Validated
@MappedEntity("TPL_FILE_T")
public class Attachment extends BaseUploadVO {

  private static final long serialVersionUID = 1757475622243875292L;

  private String attachType;
  private String fileUid;
  private Integer fileSize;
  

  public String getAttachType() {
    return attachType;
  }

  public String getFileUid() {
    return fileUid;
  }

  public void setAttachType(String attachType) {
    this.attachType = attachType;
  }

  public void setFileUid(String fileUid) {
    this.fileUid = fileUid;
  }

  public Integer getFileSize() {
    return fileSize;
  }

  public void setFileSize(Integer fileSize) {
    this.fileSize = fileSize;
  }

 
  


}
