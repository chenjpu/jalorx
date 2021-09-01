package io.jalorx.security.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.dao.BaseDao;
import io.jalorx.boot.entity.PermissionResource;

@Mapper
public interface PermissionResourceDao extends BaseDao<PermissionResource> {
  /**
   * 批量删除记录
   * 
   * @param appCode
   * @param rids resources值集合
   */
  void deleteByRescode(@Param("appCode") String appCode, @Param("rids") List<String> rids);

  /**
   * 获取所有权限项
   * 
   * @return
   */
  public List<HashMap<String, Object>> getAllPermission();

}
