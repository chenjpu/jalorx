package io.jalorx.boot.service;

import io.jalorx.boot.model.Id;

/**
 * 
 * @author csx
 *
 * @param <T>实体类
 */
public interface BaseService<T extends Id> extends Service<T, Long> {

}
