package io.jalorx.security.ui.org;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.entity.Org;
import io.jalorx.security.service.OrgService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/security/org")
@Resource(code=10120,desc = "Org Resource")
@Validated
@Operation.Create
@Tag(name = "security/org")
public class CreateResource {

  @Inject
  OrgService service;


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
  public Serializable create(@NotNull @Valid @Body Org entity) throws BusinessAccessException {
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
  public Iterable<Serializable> create(@NotNull @Valid @Body List<Org> list) throws BusinessAccessException {
    return service.save(list);
  }




}
