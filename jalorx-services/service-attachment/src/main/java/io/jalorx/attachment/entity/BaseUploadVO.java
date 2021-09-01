package io.jalorx.attachment.entity;

import io.jalorx.boot.model.LongIdVO;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class BaseUploadVO extends LongIdVO {
  private static final long serialVersionUID = -3388573895452190572L;
  private String rootPath;
  private String storewayValue;
  private String storeway;
  private String filePath;
  private String fileName;
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
