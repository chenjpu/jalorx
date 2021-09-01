package io.jalorx.security.ui.org;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.utils.StringUtils;
import io.jalorx.security.service.OrgService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/org")
@Resource(code=10120,desc = "Org Resource")
@Validated
@Operation.Delete
@Tag(name = "security/org")
public class DeleteResource {

  @Inject
  OrgService service;
  
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

}
