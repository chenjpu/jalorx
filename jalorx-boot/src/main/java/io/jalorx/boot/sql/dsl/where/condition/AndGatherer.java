/*
 *    Copyright 2016-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package io.jalorx.boot.sql.dsl.where.condition;

import java.util.function.Supplier;

/**
 * Utility class supporting the "and" part of a between condition. This class supports builders,
 * so it is mutable.
 *
 * @author Jeff Butler
 *
 * @param <T> the type of field for the between condition
 * @param <R> the type of condition being built
 */
public abstract class AndGatherer<T, R> {
    protected final T value1;
    protected T value2;

    protected AndGatherer(T value1) {
        this.value1 = value1;
    }

    public R and(T value2) {
        this.value2 = value2;
        return build();
    }

    public R and(Supplier<T> valueSupplier2) {
        return and(valueSupplier2.get());
    }

    protected abstract R build();
}
