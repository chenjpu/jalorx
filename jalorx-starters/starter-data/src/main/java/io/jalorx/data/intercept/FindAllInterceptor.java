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
package io.jalorx.data.intercept;

/**
 * An interceptor that doesn't execute a query but instead just lists all the results.
 *
 * @param <T> The declaring type
 * @param <R> The return result
 * @since 1.0
 * @author graemerocher
 */
public interface FindAllInterceptor<T, R> extends IterableResultInterceptor<T, R> {
}
