/**
 * 
 */
package io.jalorx.security.vo;

import javax.validation.constraints.NotEmpty;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenb
 *
 */
@Introspected
public class Pwd {

  @Schema(title = "旧密码")
  @NotEmpty
  private String oldPwd;
  @Schema(title = "新密码")
  @NotEmpty
  private String newPwd;

  /**
   * @return the oldPwd
   */
  public String getOldPwd() {
    return oldPwd;
  }

  /**
   * @param oldPwd the oldPwd to set
   */
  public void setOldPwd(String oldPwd) {
    this.oldPwd = oldPwd;
  }

  /**
   * @return the newPwd
   */
  public String getNewPwd() {
    return newPwd;
  }

  /**
   * @param newPwd the newPwd to set
   */
  public void setNewPwd(String newPwd) {
    this.newPwd = newPwd;
  }
}
