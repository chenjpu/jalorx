package io.jalorx.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.TreeNode;
import io.jalorx.boot.dao.BaseDao;
import io.jalorx.security.entity.Org;

/**
 * 组织DAO.
 * 
 * @author chenb
 */
@Mapper
public interface OrgDao extends BaseDao<Org> {

  int getByName(@Param("orgName") String orgName);

  Org getByCode(@Param("orgCode") String orgCode);

  List<Org> getByCodes(@Param("orgCodes") String[] orgCodes);

  List<Org> getAllByCodes(@Param("orgCodes") String[] orgCodes);

  List<Org> getListByPId(@Param("id") Long id);

  Long[] getIdsByCodes(@Param("orgCodes") String[] orgCodes);

  List<TreeNode> initTree(@Param("ids") Long[] ids);

  List<TreeNode> getChildrenByPId(@Param("id") Long pId);
}
