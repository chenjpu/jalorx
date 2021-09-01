package io.jalorx.security.ui.role;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.ui.BaseDeleteResource;
import io.jalorx.boot.utils.StringUtils;
import io.jalorx.security.entity.Role;
import io.jalorx.security.service.RoleService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;

@Controller("/security/role")
@Resource(code=10102,desc = "Role Resource")
@Validated
@Operation.Delete
@Tag(name = "security/role")
public class DeleteResource extends BaseDeleteResource<Role> {

  @Inject
  RoleService service;

  @Override
  protected RoleService getService() {
    return service;
  }

  /**
   * 给用户解除单个角色
   *
   * @param roleId 角色ID
   * @param userIds 用户集合ID
   * @return void
   */
  @Delete("/roleUser/{roleId}/{userIds}")
  @Operation(code = 94, desc = "角色用户解绑")
  public void delRoleUsersByIds(@NotEmpty @PathVariable("roleId") long roleId, @NotEmpty String userIds) {
    service.delRoleUsersByIds(roleId, StringUtils.toLongIds(userIds));
  }

  /**
   * @Description: 批量删除语句拆分，数据库执行最基本的curd
   * @Author CHENTAO
   * @param roleId
   * @param userIds
   */
  @Delete("/deleteRoleAndPermsById/{id}")
  @Operation(code = 94, desc = "角色删除")
  public Boolean deleteRoleAndPermsById(@PathVariable("id") long id) {
    //ResultInfo resultInfo = new ResultInfo();
    Long[] data = service.getUsersByRoleId(id);
    if (data.length > 0) {
      //resultInfo.setSuccess(false);
      //resultInfo.setErrors("该角色已绑定用户，请先解除绑定后再删除！");
      throw new BusinessAccessException(ErrCode.A_EXIST_LINKED_DATA);
    } else {
      //resultInfo.setSuccess(true);
      service.deleteRoleAndPermsById(id);
    }
    //return resultInfo;
    return true;
  }

}
