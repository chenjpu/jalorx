package io.jalorx.security.entity;

import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Lookup;
import io.jalorx.boot.model.CommonVO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 组.
 */
@Introspected
public class Group extends CommonVO {

  private static final long serialVersionUID = 7455231256212125627L;
  /**
   * 状态.
   */
  @Lookup(type = "STATUS")
  @NotEmpty
  @Schema(title = "状态")
  private Short status;
  
  @Schema(title = "是否系统内置标识")
  private boolean defaultIn;
  
  @Schema(title = "类型")
  private String type;

  /**
   * @return the status
   */
  public Short getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Short status) {
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isDefaultIn() {
    return defaultIn;
  }

  public void setDefaultIn(boolean defaultIn) {
    this.defaultIn = defaultIn;
  }



}
