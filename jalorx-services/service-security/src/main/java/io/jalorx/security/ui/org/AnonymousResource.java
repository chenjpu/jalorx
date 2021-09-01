package io.jalorx.security.ui.org;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.security.entity.Org;
import io.jalorx.security.service.OrgService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/org")
@Resource(10106)
@Validated
@Operation.Anonymous
@Tag(name = "security/org")
public class AnonymousResource {

  @Inject
  OrgService service;


  /**
   * 根据orgCodes集合查询Org信息
   * 
   * @param orgCodes
   * @return
   */
  @Get("/getByCodes/{orgCodes}")
  public List<Org> getOrgByCodes(@PathVariable("orgCodes") @NotEmpty String orgCodes) {
    return service.getByCodes(orgCodes.split(","));
  }

  /**
   * 根据id集合查询组的键值对信息
   * 
   * @param ids
   * @return
   */
  @Post(value = "/pair/info")
  public List<Org.Meta> getOrgInfo(@Body Set<Long> ids) {
    List<Org.Meta> list = new ArrayList<>(ids.size());
    for (Long id : ids) {
      Org org = service.get(id);
      if (org != null) {
        list.add(org.metaof());
      }
    }
    return list;
  }

}
