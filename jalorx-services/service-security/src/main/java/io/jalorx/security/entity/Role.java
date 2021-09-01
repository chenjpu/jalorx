package io.jalorx.security.entity;

import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Lookup;
import io.jalorx.boot.model.CommonVO;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 角色.
 * 
 * @author chenb
 */
@Introspected
public class Role extends CommonVO {

  private static final long serialVersionUID = -1449084482294160348L;
  public static final String SUPER_RIGHTS = "__ALL";
  public static String ROLE_PUBLIC = "ROLE_PUBLIC";
  public static String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

  @Lookup(type = "STATUS")
  @Schema(title = "状态")
  private short status;
  @NotEmpty
  @Schema(title = "系统内置标识0否1是")
  private boolean defaultIn;

  /**
   * @return the status
   */
  public short getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(short status) {
    this.status = status;
  }


  /**
   * @return the defaultIn
   */
  public boolean isDefaultIn() {
    return defaultIn;
  }

  /**
   * @param defaultIn the defaultIn to set
   */
  public void setDefaultIn(boolean defaultIn) {
    this.defaultIn = defaultIn;
  }

  /**
   * @return the superRights
   */
  public static String getSuperRights() {
    return SUPER_RIGHTS;
  }

}
