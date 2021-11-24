package io.jalorx.simple.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import io.jalorx.boot.service.impl.BaseServiceImpl;
import io.jalorx.simple.dao.DemoDao;
import io.jalorx.simple.dao.DemoRepository2;
import io.jalorx.simple.dao.sql.DemoSqlSupport;
import io.jalorx.simple.model.Demo;
import io.jalorx.simple.service.DemoService;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author ftl
 *
 */
@Named("demoService")
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements DemoService {

	@Inject
	DemoDao dao;
	
	@Inject
	DemoRepository2 dynamicDao;

	@Override
	protected DemoDao getDao() {
		return dao;
	}

	@Override
	public List<Serializable> getMultUsers(String id, String id2) {
		return Arrays.asList("3", "4", "5", "6");
	}

	@Override
	public List<Serializable> getMultUsers() {
		return Arrays.asList("1", "2", "3", "4");
	}

	////
	@Override
	public Demo get(Long id) {
		//return dynamicDao.findOne(DemoSqlSupport.queryPerson(id)).get();
		return super.get(id);
	}

	@Override
	public Demo getMysql(Long id) {
		//return dynamicDao.findOne(DemoSqlSupport.queryPerson(id)).get();
		return super.get(id);
	}
	
	@Override
	public Iterable<Demo> getGradAge(int age) {
		return dynamicDao.findByAgeGreaterThan(age);
		//return dynamicDao.findAll(DemoSqlSupport.queryAll(age),Pageable.from(1, 3));
	}

	@Override
	public void tranz() {
		// Demo test = dao.getById(11L);

		Demo test = new Demo();
		test.setName("ssssss");
		test.setAge(10L);
		test.createInit();
		dao.save(test);

		if (test.getId() % 2 == 0) {
			throw new NullPointerException();
		}
	}
}
