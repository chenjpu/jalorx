package io.jalorx.security.ui.perm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import javax.validation.constraints.NotNull;

import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.context.Loader;
import io.jalorx.boot.entity.PermissionOperation;
import io.jalorx.boot.entity.PermissionResource;
import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.ui.BaseReadResource;
import io.jalorx.security.entity.PermissionVO;
import io.jalorx.security.service.PermissionResourceService;
import io.jalorx.security.service.PermissionService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/security/permission")
@Resource(code=10103,desc = "Permission Resource")
@Validated
@Operation.Read
@Tag(name = "security/permission")
public class ReadResource extends BaseReadResource<PermissionVO> {
  

  @Inject
  PermissionService service;
  
  @Inject
  PermissionResourceService permissionResourceService;
  
  @Inject
  Loader<List<PermissionResource>> permissionResource;
  

  @Override
  protected BaseService<PermissionVO> getService() {
    return service;
  }

  /**
   * 查询所有的权限
   * 
   * @param
   * @return
   */
  @Get("/scanall")
  public List<PermissionOperation> scanAll() {
    
    List<PermissionOperation> list = new ArrayList<PermissionOperation>();
    
    permissionResource.load().forEach(res ->{
      
      String app_code = res.getAppCode();
      
      String parent_code = res.getCode();
      
      PermissionOperation root = new PermissionOperation();
      
      root.setCode(app_code + "." + res.getCode());
      root.setParentCode(res.getModel());
      root.setDesc(res.getDesc());
      list.add(root);
      res.getPermissionOperations().forEach(opt->{
        PermissionOperation po = new PermissionOperation();
        String code = opt.getCode();
        if ("Jalor".equals(parent_code)) {
          po.setCode(app_code + "." + code);
          po.setParentCode(parent_code);
        } else {
          po.setCode(app_code + "." + parent_code + "." + code);
          po.setParentCode(app_code + "." + parent_code);
        }
        po.setDesc(opt.getDesc());
        list.add(po);
      });
    });
    
    return list;
    //return permissionResourceService.getAllPermission();
  }

  /**
   * 查询角色Id查询权限
   * 
   * @param
   * @return
   */
  @Get("/role/{id}")
  @Operation(code = 202, desc = "角色权限")
	public Set<Serializable> getPermissions(@NotNull @PathVariable("id") long id) {
    return service.getPermissionsByRoleId(id);
  }
}
