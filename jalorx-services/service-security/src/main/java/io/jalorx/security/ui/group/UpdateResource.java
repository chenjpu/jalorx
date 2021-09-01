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
import io.jalorx.boot.ui.BaseUpdateResource;
import io.jalorx.security.entity.Group;
import io.jalorx.security.service.GroupService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Put;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/group")
@Resource(code=10107,desc = "Group Resource")
@Validated
@Operation.Update
@Tag(name = "security/group")
public class UpdateResource extends BaseUpdateResource<Group> {

  @Inject
  GroupService service;

  @Override
  protected BaseService<Group> getService() {
    return service;
  }

  /**
   * 修改对象 API： <pre>
   *    Method：  PUT
   *    Url   : serives/{module}/single
   *    Body  ： json格式对象
   * </pre>
   * 
   * @param o 业务对象
   * @return 业务对象唯一标识符
   * @throws BusinessAccessException 业务异常
   */
  @Put("/single")
  public Serializable update(@NotNull @Valid @Body Group entity) throws BusinessAccessException {
    service.update(entity);
    return entity.getId();
  }


  /**
   * 批量修改对象 API： <pre>
   *    Method：  PUT
   *    Url   : serives/{module}/batch
   *    Body  ： json格式对象
   * </pre>
   * 
   * @param list 业务对象集合
   * @throws BusinessAccessException 业务异常
   */
  @Put("/batch")
  public void update(@NotNull @Valid @Body List<Group> list) throws BusinessAccessException {
    service.update(list);
  }

}
