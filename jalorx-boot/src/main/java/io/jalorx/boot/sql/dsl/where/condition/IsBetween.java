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

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import io.jalorx.boot.sql.dsl.AbstractTwoValueCondition;

public class IsBetween<T> extends AbstractTwoValueCondition<T> {
    private static final IsBetween<?> EMPTY = new IsBetween<Object>(null, null) {
        @Override
        public boolean shouldRender() {
            return false;
        }
    };

    public static <T> IsBetween<T> empty() {
        @SuppressWarnings("unchecked")
        IsBetween<T> t = (IsBetween<T>) EMPTY;
        return t;
    }

    protected IsBetween(T value1, T value2) {
        super(value1, value2);
    }

    @Override
    public String renderCondition(String columnName, String placeholder1, String placeholder2) {
        return columnName + " between " + placeholder1 + " and " + placeholder2; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * If renderable and the values match the predicate, returns this condition. Else returns a condition
     *     that will not render.
     *
     * @deprecated replaced by {@link IsBetween#filter(BiPredicate)}
     * @param predicate predicate applied to the values, if renderable
     * @return this condition if renderable and the values match the predicate, otherwise a condition
     *     that will not render.
     */
    @Deprecated
    public IsBetween<T> when(BiPredicate<T, T> predicate) {
        return filter(predicate);
    }

    /**
     * If renderable, apply the mappings to the values and return a new condition with the new values. Else return a
     *     condition that will not render (this).
     *
     * @deprecated replaced by {@link IsBetween#map(Function, Function)}
     * @param mapper1 a mapping function to apply to the first value, if renderable
     * @param mapper2 a mapping function to apply to the second value, if renderable
     * @return a new condition with the result of applying the mappers to the values of this condition,
     *     if renderable, otherwise a condition that will not render.
     */
    @Deprecated
    public IsBetween<T> then(UnaryOperator<T> mapper1, UnaryOperator<T> mapper2) {
        return map(mapper1, mapper2);
    }

    @Override
    public IsBetween<T> filter(BiPredicate<? super T, ? super T> predicate) {
        return filterSupport(predicate, IsBetween::empty, this);
    }

    @Override
    public IsBetween<T> filter(Predicate<? super T> predicate) {
        return filterSupport(predicate, IsBetween::empty, this);
    }

    /**
     * If renderable, apply the mappings to the values and return a new condition with the new values. Else return a
     *     condition that will not render (this).
     *
     * @param mapper1 a mapping function to apply to the first value, if renderable
     * @param mapper2 a mapping function to apply to the second value, if renderable
     * @param <R> type of the new condition
     * @return a new condition with the result of applying the mappers to the values of this condition,
     *     if renderable, otherwise a condition that will not render.
     */
    public <R> IsBetween<R> map(Function<? super T, ? extends R> mapper1, Function<? super T, ? extends R> mapper2) {
        return mapSupport(mapper1, mapper2, IsBetween::new, IsBetween::empty);
    }

    /**
     * If renderable, apply the mapping to both values and return a new condition with the new values. Else return a
     *     condition that will not render (this).
     *
     * @param mapper a mapping function to apply to both values, if renderable
     * @param <R> type of the new condition
     * @return a new condition with the result of applying the mappers to the values of this condition,
     *     if renderable, otherwise a condition that will not render.
     */
    public <R> IsBetween<R> map(Function<? super T, ? extends R> mapper) {
        return map(mapper, mapper);
    }

    public static <T> Builder<T> isBetween(T value1) {
        return new Builder<>(value1);
    }

    public static <T> WhenPresentBuilder<T> isBetweenWhenPresent(T value1) {
        return new WhenPresentBuilder<>(value1);
    }

    public static class Builder<T> extends AndGatherer<T, IsBetween<T>> {
        private Builder(T value1) {
            super(value1);
        }

        @Override
        protected IsBetween<T> build() {
            return new IsBetween<>(value1, value2);
        }
    }

    public static class WhenPresentBuilder<T> extends AndGatherer<T, IsBetween<T>> {
        private WhenPresentBuilder(T value1) {
            super(value1);
        }

        @Override
        protected IsBetween<T> build() {
            return new IsBetween<>(value1, value2).filter(Objects::nonNull);
        }
    }
}
