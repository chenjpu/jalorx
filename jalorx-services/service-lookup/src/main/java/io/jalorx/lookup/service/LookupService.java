package io.jalorx.lookup.service;


import java.util.List;
import java.util.function.Consumer;

import io.jalorx.boot.Pair;
import io.jalorx.boot.json.MetaDataClient;
import io.jalorx.boot.service.BaseService;
import io.jalorx.lookup.entity.Lookup;
import io.micronaut.transaction.annotation.ReadOnly;


/**
 * @author chenb
 *
 */
public interface LookupService extends BaseService<Lookup>,MetaDataClient<Lookup.Meta,Pair> {

  @ReadOnly
  List<Lookup> lookupByParentId(Long parentId);

  @ReadOnly
  List<Lookup> lookupByGroupCode(String groupCode);

  @ReadOnly
  Lookup getLookupByCode(String code, String groupCode);

  @ReadOnly
  List<Lookup> getLookupByCodes(String[] code, String groupCode);

  @ReadOnly
  boolean loadAll(Consumer<Lookup> fun);

}
