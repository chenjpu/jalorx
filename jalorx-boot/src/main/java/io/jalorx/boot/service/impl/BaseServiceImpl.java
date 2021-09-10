package io.jalorx.boot.service.impl;

import io.jalorx.boot.model.Id;
import io.jalorx.boot.repository.BaseRepository;
import io.jalorx.boot.service.BaseService;

/**
 * 主键类型为long的服务实现
 * 
 * @author chenb
 *
 * @param <T>业务对象类型
 */
public abstract class BaseServiceImpl<T extends Id<Long>> extends GenericServiceImpl<T, Long>
		implements
		BaseService<T> {
	protected abstract BaseRepository<T> getDao();
	
	
}
