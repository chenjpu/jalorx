package io.jalorx.lookup.service;


import javax.transaction.Transactional;

import io.jalorx.boot.service.BaseService;
import io.jalorx.lookup.entity.Area;


/**
 * @author chenb
 *
 */
@Transactional
public interface AreaService extends BaseService<Area> {

}
