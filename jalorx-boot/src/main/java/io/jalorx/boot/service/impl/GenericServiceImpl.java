package io.jalorx.boot.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.Pageable;
import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.model.Id;
import io.jalorx.boot.repository.GenericRepository;
import io.jalorx.boot.service.Service;
import io.jalorx.boot.sql.QueryDsl;
import io.jalorx.boot.sql.QueryDslSelectProvider;
import io.jalorx.boot.sql.SelectProvider;
import io.jalorx.boot.utils.DaoUtils;
import io.micronaut.core.reflect.GenericTypeUtils;
import io.micronaut.core.util.CollectionUtils;
import jakarta.inject.Inject;

/**
 * 通用服务接口的实现
 * 
 * @author chenb
 *
 * @param <T>  业务对象类型
 * @param <PK> 主键类型
 */
public abstract class GenericServiceImpl<T extends Id<PK>, PK extends Serializable> implements Service<T, PK> {

	protected abstract GenericRepository<T, PK> getDao();

	@Inject
	private QueryDslSelectProvider dslSelectProvider;

	private Class<?> entityClass;

	public GenericServiceImpl() {
		this.entityClass = getEntityClass(this.getClass());
	}

	protected QueryDslSelectProvider getDslSelectProvider() {
		return dslSelectProvider;
	}

	// @Cacheable(cacheNames = "id", key = "#id")
	public T get(PK id) throws BusinessAccessException {
		return getDao().findById(id).orElse(null);
	}

	// @Cacheable(cacheNames = "list", key = "#ids")
	public List<T> get(PK[] ids) throws BusinessAccessException {
		if (ArrayUtils.isEmpty(ids)) {
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST);
		}
		return getDao().getByIdIn(ids);
	}

	// @CachePut(cacheNames = "id", key = "#o.id")
	// @CacheEvict(cacheNames = {"list", "other"}, allEntries = true)
	public T save(T o) throws BusinessAccessException {
		o.createInit();
		return getDao().save(o);
	}

	// @CacheEvict(cacheNames = {"list", "other"}, allEntries = true)
	public Iterable<Serializable> save(List<T> list) throws BusinessAccessException {
		if (CollectionUtils.isEmpty(list)) {
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST);
		}
		List<Serializable> ids = new ArrayList<>(list.size());
		for (T o : list) {
			o.createInit();
		}
		for (T o : getDao().saveAll(list)) {
			ids.add(o.getId());
		}
		return ids;
	}

	// @Cacheable(cacheNames = "list")
	public Iterable<T> getAll() throws BusinessAccessException {
		return getDao().findAll();
	}

	// @Cacheable(cacheNames = "list", key = "#filter")
	public Iterable<T> getAll(QueryDsl filter) throws BusinessAccessException {
		if (entityClass != null) {
			SelectProvider sp = dslSelectProvider.from(filter, entityClass);
			return getDao().findAll(sp);
		} else {
			return getDao().findAll();
		}
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
		getDao().deleteByIdIn(ids);
	}

	// @Cacheable(cacheNames = "id", key = "#id")
	public T find(PK id) throws BusinessAccessException {
		T t = getDao().findById(id).orElse(null);
		DaoUtils.notNull(t, id);
		return t;
	}

	// @Cacheable(cacheNames = "list", keyGenerator = "cache.pageableKey")
	public Pageable<T> getAll(int page, int pageSize) throws BusinessAccessException {
		this.validatePageSize(pageSize);
		// int length = getDao().getCount();
		// page = DaoUtils.realPage(page, pageSize, length);
		return Pageable.of(getDao().findAll(DaoUtils.from(page, pageSize)));
	}

	// @Cacheable(cacheNames = "list", keyGenerator = "cache.pageableKey")
	public Pageable<T> getAll(QueryDsl filter, int page, int pageSize) throws BusinessAccessException {
		this.validatePageSize(pageSize);
		if (entityClass != null) {
			SelectProvider sp = dslSelectProvider.from(filter, entityClass);
			return Pageable.of(getDao().findAll(sp, DaoUtils.from(page, pageSize)));
		} else {
			return Pageable.of(getDao().findAll(DaoUtils.from(page, pageSize)));
		}
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
		}
		getDao().updateAll(list);
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

	protected Class<?> getEntityClass(Class<?> superclass) {
		Optional<Class> clazz = GenericTypeUtils.resolveSuperGenericTypeArgument(superclass);
		if (clazz.isPresent() || superclass == Object.class) {
			return clazz.orElse(null);
		}
		return getEntityClass(superclass.getSuperclass());
	}

}
