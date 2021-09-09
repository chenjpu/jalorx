package io.jalorx.security.entity;

import io.jalorx.boot.model.LongIdVO;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 日志.
 */
@Introspected
@Validated
@MappedEntity("tpl_action_t")
public class Action extends LongIdVO {

  private static final long serialVersionUID = -693339168103589607L;

  @Schema(title = "操作码")
  private int operCode;
  @Schema(title = "操作描述")
  private String operDesc;
  @Schema(title = "资源码")
  private int resCode;
  @Schema(title = "资源描述")
  private String resDesc;
  @Schema(title = "方法名")
  private String methodName;
  @Schema(title = "IP地址")
  private String ipAddress;
  @Schema(title = "参数")
  private String paramDesc;

  public int getOperCode() {
    return operCode;
  }

  public String getOperDesc() {
    return operDesc;
  }

  public int getResCode() {
    return resCode;
  }

  public String getResDesc() {
    return resDesc;
  }

  public String getMethodName() {
    return methodName;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public String getParamDesc() {
    return paramDesc;
  }

  public void setOperCode(int operCode) {
    this.operCode = operCode;
  }

  public void setOperDesc(String operDesc) {
    this.operDesc = operDesc;
  }

  public void setResCode(int resCode) {
    this.resCode = resCode;
  }

  public void setResDesc(String resDesc) {
    this.resDesc = resDesc;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public void setParamDesc(String paramDesc) {
    this.paramDesc = paramDesc;
  }


}
