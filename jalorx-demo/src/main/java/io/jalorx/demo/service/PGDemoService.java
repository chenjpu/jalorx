package io.jalorx.demo.service;

import javax.transaction.Transactional;

import io.jalorx.boot.datasource.annotation.DS;
import io.jalorx.boot.service.BaseService;
import io.jalorx.demo.model.Demo;

@Transactional
@DS("PG")
public interface PGDemoService extends BaseService<Demo> {}
