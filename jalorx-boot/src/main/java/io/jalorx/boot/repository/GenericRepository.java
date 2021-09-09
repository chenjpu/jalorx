package io.jalorx.boot.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import io.jalorx.boot.POJO;
import io.jalorx.boot.sql.SelectProvider;
import io.micronaut.core.annotation.Blocking;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.validation.Validated;


@Blocking
@Validated
public interface GenericRepository<E extends POJO, ID extends Serializable> extends PageableRepository<E, ID> {

	@NonNull
	Optional<E> findOne(@NonNull SelectProvider sqlProvider);

	@NonNull
	Iterable<E> findAll(@NonNull SelectProvider sqlProvider);

	boolean exists(@NonNull SelectProvider sqlProvider);

	long count(@NonNull SelectProvider sqlProvider);
	
	@NonNull
	Page<E> findAll(@NonNull SelectProvider sqlProvider, @NonNull Pageable pageable);
	
	
	List<E> getByIdIn(ID[] ids);
	
	int deleteByIdIn(ID[] ids);

}
