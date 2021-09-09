package io.jalorx.lookup.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.Pair;
import io.jalorx.boot.annotation.Cache;
import io.jalorx.boot.service.impl2.BaseServiceImpl;
import io.jalorx.lookup.dao.LookupDao;
import io.jalorx.lookup.entity.Lookup;
import io.jalorx.lookup.service.LookupService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * @author chenb
 *
 */
@Singleton
@Cache(name = "JalorX:Lookup", expire = 24 * 60 * 60)
@Named("Lookup.MetaDataClient")
public class LookupServiceImpl extends BaseServiceImpl<Lookup> implements LookupService {

  static final Logger LOGGER = LoggerFactory.getLogger(LookupServiceImpl.class);

  @Inject
  LookupDao dao;

  @Override
  protected LookupDao getDao() {
    return dao;
  }

  @Override
  public List<Lookup> lookupByParentId(Long parentId) {
    return getDao().findByParentId(parentId);
  }

  @Override
  public List<Lookup> lookupByGroupCode(String groupCode) {
    return getDao().findByGroupCode(groupCode);
  }

  @Override
  public Lookup getLookupByCode(String code, String groupCode) {
    return getDao().getByCodeAndGroupCode(code, groupCode);
  }

  @Override
  public List<Lookup> getLookupByCodes(String[] codes, String groupCode) {
    return getDao().findByCodeInAndGroupCode(codes, groupCode);
  }

  /**
   * 如果缓存过则不再执行
   */
  @Override
  public boolean loadAll(Consumer<Lookup> fun) {
    //long starttime = System.currentTimeMillis();
    Iterable<Lookup> list = this.getAll();
    // 传入setLookupByCode方法
    list.forEach(fun::accept);
    //long endtime = System.currentTimeMillis();
    //LOGGER.info("Total of " + list.size() + " records and cost " + (endtime - starttime) + " ms");
    return true;
  }

  @Override
  public List<Lookup.Meta> getDetails(Set<Pair> ids) {
    List<Lookup.Meta> list = new ArrayList<>(ids.size());
    
    for (Pair id : ids) {
      Lookup user = dao.getByCodeAndGroupCode(id.getValue(), id.getKey());
      if (user != null) {
        list.add(user.metaof());
      } else {
        list.add(new Lookup.Meta(id.getKey(), id.getValue()));
      }
    }
    return list;
  }
}
