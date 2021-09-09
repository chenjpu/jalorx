/**
 * 
 */
package io.jalorx.simple.service;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import io.jalorx.boot.datasource.annotation.DS;
import io.jalorx.boot.service.BaseService;
import io.jalorx.simple.model.Demo;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * @author ftl
 *
 */
@Transactional
@DS
public interface DemoService extends BaseService<Demo> {

	@ReadOnly
	List<Serializable> getMultUsers(String id, String id2);

	@ReadOnly
	List<Serializable> getMultUsers();

	@ReadOnly
	Demo get(Long id);

	@DS
	@ReadOnly
	Demo getMysql(Long id);

	@DS
	@Transactional
	void tranz();

	Iterable<Demo> getGradAge(int age);
}
