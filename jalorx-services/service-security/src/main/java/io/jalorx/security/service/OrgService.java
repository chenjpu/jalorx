package io.jalorx.security.service;

import java.util.List;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import io.jalorx.boot.service.BaseService;
import io.jalorx.boot.service.CascaderService;
import io.jalorx.security.entity.Org;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * 组织机构管理服务接口
 * 
 * @Author: lsn
 * @Version:v1.0
 */
@Transactional
public interface OrgService extends BaseService<Org>, CascaderService<Long> {

  /**
   * 根据机构名称获取机构信息，用户校验机构名称是否唯一不重复.
   * 
   * @param orgName 机构名称
   * @return int
   */
  @ReadOnly
  int getByName(String orgName);

  /**
   * 根据机构编码查询机构信息
   * 
   * @param orgCode 机构编码
   * @return Org
   */
  @ReadOnly
  Org getByCode(String orgCode);

  /**
   * 根据机构编码批量查询机构信息
   * 
   * @param orgCodes 机构编码（多个）
   * @return List<Org>
   */
  @ReadOnly
  List<Org> getByCodes(String[] orgCodes);

  /**
   * @Description:根据机构编码批量查询机构信息
   * @param orgCodes 机构编码（多个）
   * @return List<Org>
   */
  @ReadOnly
  List<Org> getAllByCodes(String[] orgCodes);

  /**
   * 
   * @Description: 删除父节点，子节点级联删除
   * @Author CHENTAO
   * @param id
   * @return
   */
  @ReadOnly
  List<Org> getListByPId(Long id);


  /**
   * 通过code编码，查询指定的id集合
   * 
   * @param orgCodes
   * @return
   */
  @ReadOnly
  Long[] getIdsByCodes(String[] orgCodes);

  /**
   * 加载Org数据
   * 
   * @param fun
   * @return
   */
  @ReadOnly
  boolean loadAll(Consumer<Org> fun);

  /**
   * 设置Org到缓存
   * 
   * @param org
   * @param orgCode
   * @return
   */
  @ReadOnly
  Org setByCode(Org org, String orgCode);

}

