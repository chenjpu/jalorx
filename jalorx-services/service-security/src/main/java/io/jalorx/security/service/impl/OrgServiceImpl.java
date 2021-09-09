package io.jalorx.security.service.impl;

import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.TreeNode;
import io.jalorx.boot.annotation.Cache;
import io.jalorx.boot.service.impl2.BaseServiceImpl;
import io.jalorx.security.dao.OrgDao;
import io.jalorx.security.entity.Org;
import io.jalorx.security.service.OrgService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * 组织Service
 * 
 * @author chenb
 */
@Singleton
@Cache(name = "JalorX:Org")
public class OrgServiceImpl extends BaseServiceImpl<Org> implements OrgService {

  static final Logger LOGGER = LoggerFactory.getLogger(OrgServiceImpl.class);

  @Inject
  OrgDao dao;

  @Override
  protected OrgDao getDao() {
    return dao;
  }

  @Override
  public int getByName(String orgName) {
    return getDao().countOrgName(orgName);
  }

  @Override
  public Org getByCode(String orgCode) {
    return getDao().getByOrgCode(orgCode);
  }

  @Override
  public List<Org> getByCodes(String[] orgCodes) {
    return getDao().getByOrgCodeIn(orgCodes);
  }

  @Override
  public List<Org> getAllByCodes(String[] orgCodes) {
    return getDao().getByOrgCodeIn(orgCodes);
  }

  /**
   * 递归删除父子节点
   */
  @Override
  public void remove(Long id) {
    List<Org> list = getDao().getByPId(id);
    dao.deleteById(id);
    if (list == null) {
      return;
    } else {
      for (Org org : list) {
        remove(org.getId());
      }
    }
  }

  /**
   * 通过父ID得到子列表
   */
  @Override
  public List<Org> getListByPId(Long id) {
    return getDao().getByPId(id);
  }

  @Override
  public List<TreeNode> getChildrenByPId(Long pId) {
	throw new UnsupportedOperationException();
    //return getDao().getChildrenByPId(pId);
  }

  @Override
  public List<TreeNode> getAllByIds(Long[] ids) {
	throw new UnsupportedOperationException();
    //return getDao().initTree(ids);
  }

  @Override
  public Long[] getIdsByCodes(String[] orgCodes) {
    return getDao().getIdByOrgCodeIn(orgCodes);
  }

  @Override
  public boolean loadAll(Consumer<Org> fun) {
    //long starttime = System.currentTimeMillis();
    Iterable<Org> list = this.getAll();
    // 传入setLookupByCode方法
    list.forEach(fun::accept);
    //long endtime = System.currentTimeMillis();
    //LOGGER.info("Total of " + list.size() + " records and cost " + (endtime - starttime) + " ms");
    return true;
  }

  @Override
  public Org setByCode(Org org, String orgCode) {
    return org;
  }

}
