package io.jalorx.security.entity;

import io.jalorx.boot.model.StringIdVO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 权限.
 * 
 * @author lll
 */
@Introspected
public class PermissionVO extends StringIdVO {

  private static final long serialVersionUID = 5030442800316489341L;

  @Schema(title = "编码")
  private String code;
  @Schema(title = "描述")
  private String desc; 
  @Schema(title = "父级编码")
  private String parent_code;
  @Schema(title = "项目编码")
  private String app_code;

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }



  public void setCode(String code) {
    this.code = code;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getParent_code() {
    return parent_code;
  }

  public String getApp_code() {
    return app_code;
  }

  public void setParent_code(String parent_code) {
    this.parent_code = parent_code;
  }

  public void setApp_code(String app_code) {
    this.app_code = app_code;
  }


  


}
