package io.jalorx.security.entity;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class ResultInfo {

  boolean success; // 是否成功
  String errors;// 错误消息

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getErrors() {
    return errors;
  }

  public void setErrors(String errors) {
    this.errors = errors;
  }

}
