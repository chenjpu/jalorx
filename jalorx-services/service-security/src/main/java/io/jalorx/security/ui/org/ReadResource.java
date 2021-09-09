package io.jalorx.security.ui.org;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.Pageable;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseReadResource;
import io.jalorx.boot.utils.StringUtils;
import io.jalorx.security.entity.Org;
import io.jalorx.security.service.OrgService;
import io.micronaut.http.HttpParameters;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/security/org")
@Resource(code=10120,desc = "Org Resource")
@Validated
@Operation.Read
@Tag(name = "security/org")
public class ReadResource extends BaseReadResource<Org> {

  @Inject
  OrgService service;

  @Override
  protected BaseService<Org> getService() {
    return service;
  }

  /**
   * 查询id指定的业务对象 API： <pre>
   *    Method：   GET
   *    Url   :  serives/{module}/single/{ids}
   * </pre>
   * 
   * @param ids 逗号分隔的id字符串
   * @return ids指定的对象集合
   * @throws BusinessAccessException 业务异常
   */
  @Get("/single/{id}")
  @Operation(code = Operation.READ)
  public Org get(@PathVariable("id") @NotNull Long id) throws BusinessAccessException {
    return service.get(id);
  }

  /**
   * 批量查询ids指定的业务对象 API： <pre>
   *    Method：   GET
   *    Url   :  serives/{module}/batch/{ids}
   * </pre>
   * 
   * @param ids 逗号分隔的id字符串
   * @return ids指定的对象集合
   * @throws BusinessAccessException 业务异常
   */
  @Get("/batch/{ids}")
  public Iterable<Org> gets(@PathVariable("ids") @NotEmpty String ids) throws BusinessAccessException {
    Long[] pks = StringUtils.toLongIds(ids);
    return service.get(pks);
  }

  /**
   * 分页查询 API： <pre>
   *    Method：   GET
   *    Url   :  serives/{module}/list/{page}/{pageSize}?{queryString}
   * </pre>
   * 
   * @param page 页码
   * @param pageSize 也大小
   * @return 返回分页查询对象
   * @throws BusinessAccessException 业务异常
   */
  @Get("/list/{page}/{pageSize}")
  public Pageable<Org> listPage(
      @Parameter(name = "Q", in = ParameterIn.QUERY, array = @ArraySchema(schema = @Schema(type = "string")), description = QE, required = false, allowEmptyValue = true) HttpParameters params,
      @PathVariable("page") int page, @PathVariable("pageSize") int pageSize)
      throws BusinessAccessException {
    return service.getAll(getQueryFilter(params), page, pageSize);
  }

  /**
   * 查询集合 API： <pre>
   *    Method：   GET
   *    Url   :  serives/{module}/list?{queryString}
   * </pre>
   * 
   * @return 满足条件的对象集合
   * @throws BusinessAccessException 业务异常
   */
  @Get("/list")
  public Iterable<Org> list(
      @Parameter(name = "Q", in = ParameterIn.QUERY, array = @ArraySchema(schema = @Schema(type = "string")), description = QE, required = false, allowEmptyValue = true) HttpParameters params)
      throws BusinessAccessException {
    return service.getAll(getQueryFilter(params));
  }


  ////

  /**
   * 查询机构名称是否存在，用于校验机构名称唯一不重复
   * 
   * @param orgName 机构名称
   * @return String
   */
  @Get("/getByName")
  public String getByName(@QueryValue("orgName") String orgName) {
    int qResult = service.getByName(orgName);
    return (qResult + "");
  }


  /**
   * 根据传入的机构编码批量查询机构信息
   * 
   * @param orgCodes 机构编码
   * @return List<Org>
   */
  @Get("/getAllByCode")
  public List<Org> getAllByCode(@QueryValue("queryStr") String orgCodes) {
    return service.getAllByCodes(orgCodes.split(","));
  }

}
