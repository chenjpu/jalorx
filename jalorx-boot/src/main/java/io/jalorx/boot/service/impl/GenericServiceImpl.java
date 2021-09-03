package io.jalorx.boot.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.Page;
import io.jalorx.boot.Pageable;
import io.jalorx.boot.dao.GenericDao;
import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.model.Id;
import io.jalorx.boot.service.Service;
import io.jalorx.boot.sql.QueryFilter;
import io.jalorx.boot.utils.DaoUtils;
import io.micronaut.core.util.CollectionUtils;

/**
 * 通用服务接口的实现
 * 
 * @author chenb
 *
 * @param <T> 业务对象类型
 * @param <PK> 主键类型
 */
public abstract class GenericServiceImpl<T extends Id<?>, PK extends Serializable>
		extends BaseThisAware
		implements
		Service<T, PK> {

	protected abstract GenericDao<T, PK> getDao();

	// @Cacheable(cacheNames = "id", key = "#id")
	public T get(PK id) throws BusinessAccessException {
		return getDao().getById(id);
	}

	// @Cacheable(cacheNames = "list", key = "#ids")
	public List<T> get(PK[] ids) throws BusinessAccessException {
		if (ArrayUtils.isEmpty(ids)) {
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST);
		}
		return getDao().getByIds(ids);
	}

	// @CachePut(cacheNames = "id", key = "#o.id")
	// @CacheEvict(cacheNames = {"list", "other"}, allEntries = true)
	public T save(T o) throws BusinessAccessException {
		o.createInit();
		getDao().insert(o);
		return o;
	}

	// @CacheEvict(cacheNames = {"list", "other"}, allEntries = true)
	public List<Serializable> save(List<T> list) throws BusinessAccessException {
		if (CollectionUtils.isEmpty(list)) {
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST);
		}
		List<Serializable> ids = new ArrayList<>(list.size());
		for (T o : list) {
			o.createInit();
			getDao().insert(o);
			ids.add(o.getId());
		}
		return ids;
	}

	// @Cacheable(cacheNames = "list")
	public List<T> getAll() throws BusinessAccessException {
		return getDao().selectAll();
	}

	// @Cacheable(cacheNames = "list", key = "#filter")
	public List<T> getAll(QueryFilter filter) throws BusinessAccessException {
		return getDao().selectAll(filter);
	}

	// @Caching(evict = {@CacheEvict(cacheNames = "id", key = "#id"),
	// @CacheEvict(cacheNames = {"list", "other"}, allEntries = true)})
	public void remove(PK id) throws BusinessAccessException {
		getDao().deleteById(id);
	}

	// @CacheEvict(cacheNames = {"id", "list", "other"}, allEntries = true)
	public void remove(PK[] ids) throws BusinessAccessException {
		if (ArrayUtils.isEmpty(ids)) {
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST);
		}
		getDao().deleteByIds(ids);
	}

	// @Cacheable(cacheNames = "id", key = "#id")
	public T find(PK id) throws BusinessAccessException {
		T t = getDao().getById(id);
		DaoUtils.notNull(t, id);
		return t;
	}

	// @Cacheable(cacheNames = "list", keyGenerator = "cache.pageableKey")
	public Pageable<T> getAll(int page, int pageSize) throws BusinessAccessException {

		this.validatePageSize(pageSize);

		int length = getDao().getCount();
		page = DaoUtils.realPage(page, pageSize, length);

		// 长度为0,直接返回
		if (length == 0) {
			return new Pageable<T>(page, pageSize, length, Collections.emptyList());
		}

		return new Pageable<T>(page, pageSize, length,
				getDao().select(new Page((page - 1) * pageSize, pageSize)));
	}

	// @Cacheable(cacheNames = "list", keyGenerator = "cache.pageableKey")
	public Pageable<T> getAll(QueryFilter filter, int page, int pageSize)
			throws BusinessAccessException {

		this.validatePageSize(pageSize);

		int length = getDao().getCount(filter);
		page = DaoUtils.realPage(page, pageSize, length);
		// 长度为0,直接返回
		if (length == 0) {
			return new Pageable<T>(page, pageSize, length, Collections.emptyList());
		}
		return new Pageable<T>(page, pageSize, length,
				getDao().select(filter, new Page((page - 1) * pageSize, pageSize)));
	}

	// @Caching(evict = {@CacheEvict(cacheNames = "id", key = "#o.id"),
	// @CacheEvict(cacheNames = {"list", "other"}, allEntries = true)})
	public void remove(T o) throws BusinessAccessException {
		getDao().deleteById(o.getId());
	}

	// @Caching(evict = {@CacheEvict(cacheNames = "id", key = "#o.id"),
	// @CacheEvict(cacheNames = {"list", "other"}, allEntries = true)})
	public T update(T o) throws BusinessAccessException {
		o.updateInit();
		getDao().update(o);
		return o;
	}

	// @CacheEvict(cacheNames = {"id", "list", "other"}, allEntries = true)
	public void update(List<T> list) throws BusinessAccessException {
		if (CollectionUtils.isEmpty(list)) {
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST);
		}
		for (T o : list) {
			o.updateInit();
			getDao().update(o);
		}
	}

	/**
	 * 验证分页信息单页大小限制
	 * 
	 * @param pageSize
	 */
	protected void validatePageSize(int pageSize) {
		if (pageSize <= 1000) {
			return;
		}
		throw new BusinessAccessException(ErrCode.A_NUMBER_EXCEEDING_LIMITS, pageSize);
	}

}
