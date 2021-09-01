package io.jalorx.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import io.jalorx.boot.dao.BaseDao;
import io.jalorx.demo.model.Demo;

@Mapper
//@TypeHint(DemoDao.class)
public interface DemoDao extends BaseDao<Demo> {

}
