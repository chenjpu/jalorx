package io.jalorx.security.service.impl;

import java.util.List;
import java.util.function.Consumer;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.TreeNode;
import io.jalorx.boot.annotation.Cache;
import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.security.dao.OrgDao;
import io.jalorx.security.entity.Org;
import io.jalorx.security.service.OrgService;

/**
 * 组织Service
 * 
 * @author chenb
 */
@Singleton
@Cache(name = "JalorX:Org")
public class OrgServiceImpl extends BaseServiceImpl<Org> implements OrgService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrgServiceImpl.class);

  @Inject
  OrgDao dao;

  @Override
  protected OrgDao getDao() {
    return dao;
  }

  @Override
  public int getByName(String orgName) {
    return getDao().getByName(orgName);
  }

  @Override
  public Org getByCode(String orgCode) {
    return getDao().getByCode(orgCode);
  }

  @Override
  public List<Org> getByCodes(String[] orgCodes) {
    return getDao().getByCodes(orgCodes);
  }

  @Override
  public List<Org> getAllByCodes(String[] orgCodes) {
    return getDao().getAllByCodes(orgCodes);
  }

  /**
   * 递归删除父子节点
   */
  @Override
  public void remove(Long id) {
    List<Org> list = getDao().getListByPId(id);
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
    return getDao().getListByPId(id);
  }

  @Override
  public List<TreeNode> getChildrenByPId(Long pId) {
    return getDao().getChildrenByPId(pId);
  }

  @Override
  public List<TreeNode> getAllByIds(Long[] ids) {
    return getDao().initTree(ids);
  }

  @Override
  public Long[] getIdsByCodes(String[] orgCodes) {
    return getDao().getIdsByCodes(orgCodes);
  }

  @Override
  public boolean loadAll(Consumer<Org> fun) {
    long starttime = System.currentTimeMillis();
    List<Org> list = this.getAll();
    // 传入setLookupByCode方法
    list.forEach(fun::accept);
    long endtime = System.currentTimeMillis();
    LOGGER.info("Total of " + list.size() + " records and cost " + (endtime - starttime) + " ms");
    return true;
  }

  @Override
  public Org setByCode(Org org, String orgCode) {
    return org;
  }

}
