package io.jalorx.security.entity;

import io.jalorx.boot.model.StringIdVO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 权限.
 * 
 * @author chenbing
 */
@Introspected
public class Permission extends StringIdVO {

  private static final long serialVersionUID = 5030442800316489341L;

  @Schema(title = "父级ID")
  private String parentId;// 父编码
  @Schema(title = "模块")
  private String model;// 模块
  @Schema(title = "名称")
  private String name; // 名称
  @Schema(title = "描述")
  private String desc;// 描述

  /**
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the desc
   */
  public String getDesc() {
    return desc;
  }

  /**
   * @param desc the desc to set
   */
  public void setDesc(String desc) {
    this.desc = desc;
  }

  /**
   * @return the parentId
   */
  public String getParentId() {
    return parentId;
  }

  /**
   * @param parentId the parentId to set
   */
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public int hashCode() {
    return this.id != null ? this.id.hashCode() : 0;
  }

  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    return this.id.equals(((Permission) o).id);
  }
}
