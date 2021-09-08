package io.jalorx.boot.dao;

import java.util.Optional;

import io.jalorx.boot.sql.SelectProvider;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

public interface DynamicDao {

	<E> Optional<E> findOne(SelectProvider<E> sqlProvider);

	<E> Iterable<E> findAll(SelectProvider<E> sqlProvider);
	
	<E> Page<E> findAll(SelectProvider<E> sqlProvider, Pageable pageable);

	boolean exists(SelectProvider<?> sqlProvider);

	long count(SelectProvider<?> sqlProvider);

}
