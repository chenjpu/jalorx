/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jalorx.data.repository.reactive;

import org.reactivestreams.Publisher;

import io.jalorx.boot.sql.SelectProvider;
import io.jalorx.data.intercept.reactive.CountReactiveInterceptor;
import io.jalorx.data.intercept.reactive.ExistsByReactiveInterceptor;
import io.jalorx.data.intercept.reactive.FindAllReactiveInterceptor;
import io.jalorx.data.intercept.reactive.FindOneReactiveInterceptor;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.intercept.annotation.DataMethod;
import io.micronaut.data.repository.GenericRepository;

/**
 * Interface for Dynamic Query using Reactive Streams.
 *
 * @param <E>  The entity type
 * @param <ID> The ID type
 *
 * @author graemerocher
 * @since 1.0.0
 */
public interface ReactiveStreamsDynamicRepository<E, ID> extends GenericRepository<E, ID> {

	@NonNull
	@SingleResult
	@DataMethod(interceptor = FindOneReactiveInterceptor.class)
	Publisher<E> findOne(@NonNull SelectProvider sqlProvider);

	@SingleResult
	@NonNull
	@DataMethod(interceptor = ExistsByReactiveInterceptor.class)
	Publisher<Boolean> exists(@NonNull SelectProvider sqlProvider);

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	@NonNull
	@DataMethod(interceptor = FindAllReactiveInterceptor.class)
	Publisher<E> findAll(@NonNull SelectProvider sqlProvider);

	/**
	 * Returns the number of entities available.
	 *
	 * @return the number of entities
	 */
	@SingleResult
	@NonNull
	@DataMethod(interceptor = CountReactiveInterceptor.class)
	Publisher<Long> count(@NonNull SelectProvider sqlProvider);

}
