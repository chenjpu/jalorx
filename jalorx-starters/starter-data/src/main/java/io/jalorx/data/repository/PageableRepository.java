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

import io.jalorx.boot.sql.SelectProvider;
import io.micronaut.core.annotation.Blocking;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

/**
 * A repository that supports pagination.
 *
 * @param <E>  The entity type
 * @param <ID> The ID type
 * @author graemerocher
 * @since 1.0.0
 */
@Blocking
public interface PageableRepository<E> extends DynamicRepository<E> {

	/**
	 * Finds all records for the given pageable.
	 *
	 * @param pageable The pageable.
	 * @return The results
	 */
	@NonNull
	Page<E> findAll(@NonNull SelectProvider sqlProvider, @NonNull Pageable pageable);
}
