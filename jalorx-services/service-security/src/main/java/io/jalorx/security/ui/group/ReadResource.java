package io.jalorx.security.ui.group;

import java.util.List;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.Pageable;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseReadResource;
import io.jalorx.boot.utils.StringUtils;
import io.jalorx.security.entity.Group;
import io.jalorx.security.service.GroupService;
import io.micronaut.http.HttpParameters;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/group")
@Resource(code=10107,desc = "Group Resource")
@Validated
@Operation.Read
@Tag(name = "security/group")
public class ReadResource extends BaseReadResource<Group> {

  @Inject
  GroupService service;
  
  @Override
  protected BaseService<Group> getService() {
    return service;
  }

  /**
   * 根据ID查询业务对象 API： <pre>
   *    Method：   GET
   *    Url   :  serives/{module}/single/{id}
   * </pre>
   * 
   * @param ids 逗号分隔的id字符串
   * @return ids指定的对象集合
   * @throws BusinessAccessException 业务异常
   */
  @Get("/single/{id}")
  public Group get(@NotNull Long id) throws BusinessAccessException {
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
  public List<Group> gets(@NotEmpty String ids) throws BusinessAccessException {
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
  // @ApiQueryParam
  public Pageable<Group> listPage(HttpParameters params, int page, int pageSize)
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
  public List<Group> list(HttpParameters params) throws BusinessAccessException {
    return service.getAll(getQueryFilter(params));
  }



  ////

  /**
   * 根据组ID获取该组已绑定用户ID信息
   * 
   * @param groupId 组ID
   * @return Long[]
   */
  @Get("/groupUser/{groupId}")
  @Operation(code = 93, desc = "组用户浏览")
  public Long[] getUsersByGroupId(long groupId) {
    return service.getUsersByGroupId(groupId);
  }

}
