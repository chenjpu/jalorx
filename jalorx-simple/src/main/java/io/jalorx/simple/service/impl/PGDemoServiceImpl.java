package io.jalorx.simple.service.impl;

import io.jalorx.boot.service.impl2.BaseServiceImpl;
import io.jalorx.simple.dao.DemoDao;
import io.jalorx.simple.model.Demo;
import io.jalorx.simple.service.PGDemoService;
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
