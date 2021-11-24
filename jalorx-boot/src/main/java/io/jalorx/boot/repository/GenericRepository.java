package io.jalorx.boot.repository;

import java.io.Serializable;
import java.util.List;

import io.jalorx.boot.POJO;
import io.micronaut.core.annotation.Blocking;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;
import io.micronaut.validation.Validated;


@Blocking
@Validated
public interface GenericRepository<E extends POJO, ID extends Serializable> extends PageableRepository<E, ID>, JpaSpecificationExecutor<E> {

	//boolean exists(@NonNull PredicateSpecification<E> spec);
	
	List<E> getByIdIn(ID[] ids);
	
	int deleteByIdIn(ID[] ids);

}
