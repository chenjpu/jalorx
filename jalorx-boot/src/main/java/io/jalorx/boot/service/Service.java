/*
 * 
 * 创建日期：2006-9-7
 * 
 * 版权所有：J.Bob
 */

package io.jalorx.boot.service;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.Pageable;
import io.jalorx.boot.datasource.annotation.DS;
import io.jalorx.boot.model.Id;
import io.jalorx.boot.sql.QueryDsl;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * 这个接口主要实现的功能和Dao接口一样，通用的功能就没有比较每个管理接口在自己实现了
 * 这也是一个比较重要的基础结构，唯一不好解决的是getObject()方法返回的是Object。
 * 需要寻找一个比较好的办法来实现，这个地方现在只能返回Object，有最终用户强行转换
 * 
 * 
 * 许多action中其实都不需要转换这个对象到特殊对象。需要的时候由用户自己决定。
 * 
 * @author chenb
 * @param <T> 业务对象类型
 * @param <ID> 主键类型
 */
public interface Service<T extends Id<?>, ID extends Serializable> {

	/**
	 * 更具id号获得对象，可以返回。如果返回null，不作相应的异常处理
	 * 
	 * @param id 业务标识符
	 * @return 返回id标识的对象或者如果不存在返回null
	 */
	@ReadOnly
	@DS.Slave
	T get(ID id) throws BusinessAccessException;

	/**
	 * 更具id号获得对象，可以返回。如果返回null，不作相应的异常处理
	 * 
	 * @param id 业务标识符数组
	 * @return 返回id标识的业务对象集合
	 */
	@ReadOnly
	@DS.Slave
	Iterable<T> get(ID[] id) throws BusinessAccessException;

	/**
	 * 更具id号获得对象，一般不应该返回null。如果返回null，应该做相应的异常处理
	 * 
	 * @param id 业务标识符
	 * @return 返回id标识的对象，不存在则抛出业务对象不存在异常
	 * @throws BusinessAccessException 业务对象不存在则异常
	 */
	@ReadOnly
	@DS.Master
	T find(ID id) throws BusinessAccessException;

	/**
	 * 获得指定范围内的对象集合
	 * 
	 * @param page 页数
	 * @param pageSize 页大小
	 * @return 满足分页条件的对象集合信息
	 * @throws BusinessAccessException 业务异常
	 */
	@ReadOnly
	@DS.Slave
	Pageable<T> getAll(int page, int pageSize) throws BusinessAccessException;

	/**
	 * 获得指定范围内的对象集合
	 * 
	 * @param query 过滤条件
	 * @param page 页数
	 * @param pageSize 页大小
	 * @return 满足过滤和分页条件的对象集合信息
	 * @throws BusinessAccessException 业务异常
	 */
	@ReadOnly
	@DS.Slave
	Pageable<T> getAll(QueryDsl query, int page, int pageSize) throws BusinessAccessException;

	/**
	 * 获得所有的对象集合
	 * 
	 * @return 所有对象集合
	 * @throws BusinessAccessException 业务异常
	 */
	@ReadOnly
	@DS.Slave
	Iterable<T> getAll() throws BusinessAccessException;

	/**
	 * 获得指定过滤条件的对象集合
	 * 
	 * @param query 过滤条件
	 * @return 满足条件的对象集合
	 * @throws BusinessAccessException 业务异常
	 */
	@ReadOnly
	@DS.Slave
	Iterable<T> getAll(QueryDsl query) throws BusinessAccessException;

	/**
	 * 存储对象 注意:这个地方应该会有检查是否存在此记录的业务逻辑
	 * 
	 * @param o 业务对象
	 * @throws BusinessAccessException 业务异常
	 * @return 业务唯一标识符
	 */
	@Transactional
	@DS.Master
	T save(T o) throws BusinessAccessException;

	/**
	 * 存储对象 注意:这个地方应该会有检查是否存在此记录的业务逻辑
	 * 
	 * @param list 业务对象集合
	 * @return 业务唯一标识符集合
	 * @throws BusinessAccessException 业务异常
	 */
	@Transactional
	@DS.Master
	Iterable<Serializable> save(List<T> list) throws BusinessAccessException;

	/**
	 * 批量修改业务对象
	 * 
	 * @param list 业务对象
	 * @throws BusinessAccessException 业务异常
	 */
	@Transactional
	@DS.Master
	void update(List<T> list) throws BusinessAccessException;

	/**
	 * 修改业务对象
	 * 
	 * @param o 业务对象
	 * @throws BusinessAccessException 业务异常
	 */
	@Transactional
	@DS.Master
	T update(T o) throws BusinessAccessException;

	/**
	 * 删除记录
	 * 
	 * @param id 主键id
	 * @throws BusinessAccessException 业务异常
	 */
	@Transactional
	@DS.Master
	void remove(ID id) throws BusinessAccessException;

	/**
	 * 批量删除记录
	 * 
	 * @param id 主键id数组
	 * @throws BusinessAccessException 业务异常
	 */
	@Transactional
	@DS.Master
	void remove(ID[] id) throws BusinessAccessException;

	/**
	 * 删除记录
	 * 
	 * @param o 业务对象
	 * @throws BusinessAccessException 业务异常
	 */
	@Transactional
	@DS.Master
	void remove(T o) throws BusinessAccessException;
}
