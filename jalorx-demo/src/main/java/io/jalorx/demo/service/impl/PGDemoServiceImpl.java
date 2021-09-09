package io.jalorx.demo.service.impl;

import io.jalorx.boot.service.impl2.BaseServiceImpl;
import io.jalorx.demo.dao.DemoDao;
import io.jalorx.demo.model.Demo;
import io.jalorx.demo.service.PGDemoService;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author ftl
 *
 */
@Named("pgDemoService")
public class PGDemoServiceImpl extends BaseServiceImpl<Demo> implements PGDemoService {

	@Inject
	DemoDao dao;

	@Override
	protected DemoDao getDao() {
		return dao;
	}

}
