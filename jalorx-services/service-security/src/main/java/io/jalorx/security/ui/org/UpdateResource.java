package io.jalorx.security.ui.org;

import java.io.Serializable;
import java.util.List;

import jakarta.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.entity.Org;
import io.jalorx.security.service.OrgService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Put;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/org")
@Resource(code=10120,desc = "Org Resource")
@Validated
@Operation.Update
@Tag(name = "security/org")
public class UpdateResource {

  @Inject
  OrgService service;


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
  public Serializable update(@NotNull @Valid @Body Org entity) throws BusinessAccessException {
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
  public void update(@NotNull @Valid @Body List<Org> list) throws BusinessAccessException {
    service.update(list);
  }

}
