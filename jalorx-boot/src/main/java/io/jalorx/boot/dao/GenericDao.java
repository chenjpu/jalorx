package io.jalorx.boot.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import io.jalorx.boot.POJO;
import io.jalorx.boot.Page;
import io.jalorx.boot.sql.QueryFilter;

/**
 * 所有dao的父接口,用于实现通常的数据库访问
 * 
 * 注意::这个接口改进,主要用了jdk1.5的特性 运用方法参数化类型.
 * 
 * @author chenb
 *
 * @param <T> 对象类型
 * @param <PK> 对象Id类型
 */
public interface GenericDao<T extends POJO, PK extends Serializable> {
	/**
	 * 从数据库查询满足条件的对象,这里我们不处理找不到结果的情况,应为存在业务要求 数据库中是否已有这条记录
	 * 
	 * @param id 逐渐值
	 * @return id指定的对象
	 * @throws DataAccessException 数据库访问异常
	 */
	T getById(@Param("id") PK id);

	/**
	 * 从数据库查询满足条件的对象,这里我们不处理找不到结果的情况,应为存在业务要求 数据库中是否已有这条记录
	 * 
	 * @param ids 逐渐值数组
	 * @return ids指定的对象集合
	 * @throws DataAccessException 数据库访问异常
	 */
	List<T> getByIds(@Param("ids") PK[] ids);

	/**
	 * 返回某类对象在数据库中的记录数,分页用的比较多
	 * 
	 * @return 总记录数
	 * @throws DataAccessException 数据库访问异常
	 */
	@MapKey("count")
	int getCount();

	/**
	 * @param filter 过滤查询条件
	 * @return 满足查询过滤查询条件的记录数
	 * @throws DataAccessException 数据库访问异常
	 */
	@MapKey("count")
	int getCount(@Param("query") QueryFilter filter);

	/**
	 * 返回数据库中此类记录的所有数据,按id排序
	 * 
	 * 注意：大数据集合影响效率
	 * 
	 * @return 所有记录集合
	 * @throws DataAccessException 数据库访问异常
	 */
	List<T> selectAll();

	/**
	 * @param filter 过滤查询条件
	 * @return 满足查询过滤查询条件的记录
	 * @throws DataAccessException 数据库访问异常
	 */
	List<T> selectAll(@Param("query") QueryFilter filter);

	/**
	 * 返回数据库中此类记录的数据,满足查询边界。
	 * 
	 * @param page 分页信息
	 * @return 满足分页条件的记录集合
	 * @throws DataAccessException 数据库访问异常
	 */
	List<T> select(@Param("page") Page page);

	/**
	 * @param filter 过滤查询条件
	 * @param page 分页信息
	 * @return 满足查询过滤和分页条件的记录
	 * @throws DataAccessException 数据库访问异常
	 */
	List<T> select(@Param("query") QueryFilter filter, @Param("page") Page page);

	/**
	 * 保存数据记录,更具id是否为空来判断是否插入新记录还是更新操作
	 * 
	 * @param entity 实体对象
	 * @throws DataAccessException 数据库访问异常
	 */
	int insert(T entity);

	/**
	 * 更新数据记录
	 * 
	 * @param entity 实体对象
	 * @throws DataAccessException 数据库访问异常
	 */
	int update(T entity);

	/**
	 * 删除记录
	 * 
	 * @param id 主键值
	 * @throws DataAccessException 数据库访问异常
	 */
	int deleteById(@Param("id") Serializable id);

	/**
	 * 批量删除记录
	 * 
	 * @param ids 主键值集合
	 * @throws DataAccessException 数据库访问异常
	 */
	int deleteByIds(@Param("ids") Serializable[] ids);

}
