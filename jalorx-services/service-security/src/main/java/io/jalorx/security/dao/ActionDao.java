package io.jalorx.security.dao;

import org.apache.ibatis.annotations.Mapper;

import io.jalorx.boot.dao.BaseDao;
import io.jalorx.security.entity.Action;

/**
 * @author lll
 */
@Mapper
public interface ActionDao extends BaseDao<Action> {
  
}
