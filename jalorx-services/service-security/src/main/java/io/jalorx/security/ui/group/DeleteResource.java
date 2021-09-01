package io.jalorx.security.ui.group;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseDeleteResource;
import io.jalorx.boot.utils.StringUtils;
import io.jalorx.security.entity.Group;
import io.jalorx.security.service.GroupService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/group")
@Resource(code=10107,desc = "Group Resource")
@Validated
@Operation.Delete
@Tag(name = "security/group")
public class DeleteResource extends BaseDeleteResource<Group> {

  @Inject
  GroupService service;
  
  @Override
  protected BaseService<Group> getService() {
    return service;
  }

  /**
   * 删除对象 API： <pre>
   *    Method：  DELETE
   *    Url   : serives/{module}/single/{id}
   * </pre>
   * 
   * @param id 业务标识符
   * @throws BusinessAccessException 业务异常
   */
  @Delete("/single/{id}")
  public void remove(@PathVariable("id")  @NotNull Long id) throws BusinessAccessException {
    service.remove(id);
  }

  /**
   * 批量删除 API： <pre>
   *    Method：  DELETE
   *    Url   : serives/{module}/batch/{ids}
   * </pre>
   * 
   * @param ids 逗号分隔的业务标识符id
   * @throws BusinessAccessException 业务异常
   */
  @Delete("/batch/{ids}")
  public void remove(@PathVariable("ids") @NotEmpty String ids) throws BusinessAccessException {
    Long[] pks = StringUtils.toLongIds(ids);
    service.remove(pks);
  }


  /**
   * 为群组解除用户绑定
   * 
   * @param groupId 组ID
   * @param userIds 用户ID
   * @return void
   */
  @Delete("/groupUser/{groupId}/{userIds}")
  @Operation(code = 94, desc = "组用户解绑")
  public void delGroupUsersByIds(@PathVariable("groupId") long groupId,@PathVariable("userIds")  @NotEmpty String userIds) {
    service.delGroupUsersByIds(groupId, StringUtils.toLongIds(userIds));
  }

}
