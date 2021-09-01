package io.jalorx.security.entity;

import io.jalorx.boot.model.LongIdVO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
public class BaseUploadVO extends LongIdVO {
  private static final long serialVersionUID = -3388573895452190572L;
  @Schema(title = "根路径")
  private String rootPath;
  private String storewayValue;
  private String storeway;
  @Schema(title = "文件路径")
  private String filePath;
  @Schema(title = "文件名称")
  private String fileName;
  @Schema(title = "批次号")
  private String batchNo;

  public String getRootPath() {
    return this.rootPath;
  }

  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public String getStorewayValue() {
    return this.storewayValue;
  }

  public void setStorewayValue(String storewayValue) {
    this.storewayValue = storewayValue;
  }

  public String getStoreway() {
    return this.storeway;
  }

  public void setStoreway(String storeway) {
    this.storeway = storeway;
  }

  public String getFilePath() {
    return this.filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getBatchNo() {
    return this.batchNo;
  }

  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }
}
