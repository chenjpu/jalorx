package io.jalorx.lookup.entity;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jalorx.boot.model.Id;
import io.jalorx.boot.model.LongIdVO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
public class Lookup extends LongIdVO {

  /**
  * 
  */
  private static final long serialVersionUID = -4952442259295730490L;

  @NotEmpty
  @Schema(title = "编码")
  private String code;

  @NotEmpty
  @Schema(title = "描述")
  private String desp;

  @NotEmpty
  @Schema(title = "类型")
  private String type;

  @Schema(title = "是否叶子节点")
  private boolean isLeaf;

  @Schema(title = "父级ID")
  private long parentId;

  @NotEmpty
  @Schema(title = "群组编码")
  private String groupCode;

  @JsonIgnore
  @Schema(title = "父级编码")
  private String parentCode;

  @JsonIgnore
  @Schema(title = "父级描述")
  private String parentDesp;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesp() {
    return desp;
  }

  public void setDesp(String desp) {
    this.desp = desp;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getParentId() {
    return parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }

  public String getGroupCode() {
    return groupCode;
  }

  public void setGroupCode(String groupCode) {
    this.groupCode = groupCode;
  }

  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String parentCode) {
    this.parentCode = parentCode;
  }

  public String getParentDesp() {
    return parentDesp;
  }

  public void setParentDesp(String parentDesp) {
    this.parentDesp = parentDesp;
  }

  public boolean isLeaf() {
    return isLeaf;
  }

  public void setLeaf(boolean isLeaf) {
    this.isLeaf = isLeaf;
  }


  @JsonIgnore
  public Meta metaof() {
    Meta m = new Meta(this.groupCode, this.code);
    m.desp = this.desp;
    return m;
  }

  @Introspected
  public static class Meta implements Id {
    /**
     * 
     */
    private static final long serialVersionUID = 6904528597631151959L;
    @Schema(title = "编码")
    private String id;

    @Schema(title = "描述")
    private String desp;

    public Meta(String type, String code) {
      this.id = type + "-" + code;
    }

    @Override
    public Serializable getId() {
      return id;
    }

    public String getDesp() {
      return desp;
    }

    public void setDesp(String desp) {
      this.desp = desp;
    }
  }
}
