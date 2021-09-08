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
package io.jalorx.data.repository;

import java.util.Optional;

import io.jalorx.boot.sql.SelectProvider;
import io.jalorx.data.intercept.CountInterceptor;
import io.jalorx.data.intercept.ExistsByInterceptor;
import io.jalorx.data.intercept.FindAllInterceptor;
import io.jalorx.data.intercept.FindOneInterceptor;
import io.jalorx.data.intercept.FindOptionalInterceptor;
import io.micronaut.core.annotation.Blocking;
import io.micronaut.core.annotation.Indexed;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.intercept.annotation.DataMethod;
import io.micronaut.validation.Validated;

/**
 * A repository interface for performing Dynamic Query. This is a blocking
 * variant and is largely based on the same interface in Spring Data, however
 * includes integrated validation support.
 *
 * @author graemerocher
 * @since 1.0
 * @param <E>  The entity type
 * @param <ID> The ID type
 */
@Blocking
@Validated
@Indexed(DynamicRepository.class)
public interface DynamicRepository<E> {

	@NonNull
	@DataMethod(interceptor = FindOneInterceptor.class)
	Optional<E> getOne(@NonNull SelectProvider sqlProvider);

	@NonNull
	@DataMethod(interceptor = FindOptionalInterceptor.class)
	Optional<E> findOne(@NonNull SelectProvider sqlProvider);

	@NonNull
	@DataMethod(interceptor = FindAllInterceptor.class)
	Iterable<E> findAll(@NonNull SelectProvider sqlProvider);

	@DataMethod(interceptor = ExistsByInterceptor.class)
	boolean exists(@NonNull SelectProvider sqlProvider);

	@DataMethod(interceptor = CountInterceptor.class)
	long count(@NonNull SelectProvider sqlProvider);

}
