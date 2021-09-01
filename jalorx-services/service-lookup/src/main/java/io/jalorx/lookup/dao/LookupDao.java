/**
 * 
 */
package io.jalorx.lookup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.dao.BaseDao;
import io.jalorx.lookup.entity.Lookup;


/**
 * @author chenb
 *
 */
@Mapper
public interface LookupDao extends BaseDao<Lookup> {

  List<Lookup> selectTreeById(Long id);

  List<Lookup> lookupByParentId(Long parentId);

  List<Lookup> lookupByGroupCode(String groupCode);

  Lookup getLookupByCode(@Param("code") String code, @Param("groupCode") String groupCode);

  List<Lookup> getLookupByCodes(@Param("codes") String[] codes,
      @Param("groupCode") String groupCode);

}
