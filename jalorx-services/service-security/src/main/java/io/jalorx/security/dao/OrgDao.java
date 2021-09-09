package io.jalorx.security.dao;

import java.util.List;

import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.security.entity.Org;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

/**
 * 组织DAO.
 * 
 * @author chenb
 */
@JdbcRepository(dialect = Dialect.MYSQL)
public interface OrgDao extends BaseRepository<Org> {

  int countOrgName(String orgName);

  Org getByOrgCode(String orgCode);

  List<Org> getByOrgCodeIn(String[] orgCodes);

  //List<Org> getAllByCodes(@Param("orgCodes") String[] orgCodes);
  
  List<Org> getByPId(Long id);

  Long[] getIdByOrgCodeIn(String[] orgCodes);

  //List<TreeNode> initTree(Long[] ids);

  //List<TreeNode> getChildrenByPId(Long pId);
}
