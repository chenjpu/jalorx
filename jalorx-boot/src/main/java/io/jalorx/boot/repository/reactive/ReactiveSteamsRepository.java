package io.jalorx.boot.repository.reactive;

import org.reactivestreams.Publisher;

import io.jalorx.boot.sql.SelectProvider;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.repository.GenericRepository;

public interface ReactiveSteamsRepository<E, ID> extends GenericRepository<E, ID> {

	@NonNull
	@SingleResult
	Publisher<E> findOne(@NonNull SelectProvider sqlProvider);

	@SingleResult
	@NonNull
	Publisher<Boolean> existsBySql(@NonNull SelectProvider sqlProvider);

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	@NonNull
	Publisher<E> findAll(@NonNull SelectProvider sqlProvider);

	/**
	 * Returns the number of entities available.
	 *
	 * @return the number of entities
	 */
	@SingleResult
	@NonNull
	Publisher<Long> count(@NonNull SelectProvider sqlProvider);

}
