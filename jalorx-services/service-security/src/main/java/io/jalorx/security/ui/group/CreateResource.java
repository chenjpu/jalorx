package io.jalorx.security.ui.group;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseCreateResource;
import io.jalorx.security.entity.Group;
import io.jalorx.security.service.GroupService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/group")
@Resource(code=10107,desc = "Group Resource")
@Validated
@Operation.Create
@Tag(name = "security/group")
public class CreateResource extends BaseCreateResource<Group> {

  @Inject
  GroupService service;
  
  @Override
  protected BaseService<Group> getService() {
    return service;
  }

  /**
   * 创建对象 API： <pre>
   *    Method：  POST
   *    Url   : serives/{module}/single
   *    Body  ： json格式对象
   * </pre>
   * 
   * @param entity 业务对象
   * @return 业务对象的唯一标识符
   * @throws BusinessAccessException 业务异常
   */
  @Post("/single")
  public Serializable create(@NotNull @Valid @Body Group entity) throws BusinessAccessException {
    service.save(entity);
    return entity.getId();
  }


  /**
   * 批量创建对象 API： <pre>
   *    Method：  POST
   *    Url   : serives/{module}/batch
   *    Body  ： json格式对象
   * </pre>
   * 
   * @param list 业务对象集合
   * @return 返回标识符集合 
   * @throws BusinessAccessException 业务异常
   */
  @Post("/batch")
  public Iterable<Serializable> create(@NotNull @Valid @Body List<Group> list) throws BusinessAccessException {
    return service.save(list);
  }

  /////


  /**
   * 根据组IDs获取该组已绑定用户Email信息
   * 
   * @param groupIds 组ID
   * @return String[]
   */
  @Post("/emails/groupIds")
  public String[] getUserEmailsByGroupIds(@NotNull @Body Long[] groupIds) {
    return service.getUserEmailsByGroupIds(groupIds);
  }



  /**
   * 为某群组绑定用户信息（组ID＝〉用户ID[]映射表）
   * 
   * @param groupId 组ID
   * @return void
   */
  @Post("/groupUser/{groupId}")
  @Operation(code = 91, desc = "组用户绑定")
  public void groupUserSetting(@PathVariable("groupId")  long groupId, @Body Long[] userIds) {
    service.groupUserSetting(groupId, userIds);
  }





}
