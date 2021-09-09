package io.jalorx.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.jalorx.boot.entity.PermissionOperation;
import io.jalorx.boot.entity.PermissionResource;
import io.jalorx.boot.service.impl2.BaseServiceImpl;
import io.jalorx.security.dao.PermissionResourceDao;
import io.jalorx.security.service.PermissionResourceService;

@Singleton
public class PermissionResourceServiceImpl extends BaseServiceImpl<PermissionResource>
    implements
      PermissionResourceService {

  @Inject
  PermissionResourceDao dao;

  @Override
  protected PermissionResourceDao getDao() {
    return dao;
  }

  public List<PermissionOperation> getAllPermission() {
    List<PermissionOperation> list = new ArrayList<PermissionOperation>();
    List<HashMap<String, Object>> permissionList = getDao().getAllPermission();
    if (permissionList != null && permissionList.size() > 0) {
      permissionList.stream().forEach(permission -> {
        PermissionOperation po = new PermissionOperation();
        String app_code = permission.get("app_code").toString();
        String code = permission.get("code").toString();
        String parent_code = permission.get("parent_code").toString();
        if ("Jalor".equals(parent_code)) {
          po.setCode(app_code + "." + code);
          po.setParentCode(parent_code);
        } else {
          po.setCode(app_code + "." + parent_code + "." + code);
          po.setParentCode(app_code + "." + parent_code);
        }
        po.setDesc(permission.get("desc").toString());
        list.add(po);
      });
    }
    return list;
  }

}
