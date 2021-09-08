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

import static io.jalorx.boot.sql.dsl.util.StringUtilities.spaceAfter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.jalorx.boot.sql.dsl.AbstractListValueCondition;
import io.jalorx.boot.sql.dsl.Callback;

public class IsNotIn<T> extends AbstractListValueCondition<T> {
    private static final IsNotIn<?> EMPTY = new IsNotIn<>(Collections.emptyList());

    public static <T> IsNotIn<T> empty() {
        @SuppressWarnings("unchecked")
        IsNotIn<T> t = (IsNotIn<T>) EMPTY;
        return t;
    }

    protected IsNotIn(Collection<T> values) {
        super(values);
    }

    protected IsNotIn(Collection<T> values, Callback emptyCallback) {
        super(values, emptyCallback);
    }

    @Override
    public String renderCondition(String columnName, Stream<String> placeholders) {
        return spaceAfter(columnName)
                + placeholders.collect(
                        Collectors.joining(",", "not in (", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    @Override
    public IsNotIn<T> withListEmptyCallback(Callback callback) {
        return new IsNotIn<>(values, callback);
    }

    /**
     * This method allows you to modify the condition's values before they are placed into the parameter map.
     * For example, you could filter nulls, or trim strings, etc. This process will run before final rendering of SQL.
     * If you filter values out of the stream, then final condition will not reference those values. If you filter all
     * values out of the stream, then the condition will not render.
     *
     * @deprecated replaced by {@link IsNotIn#map(Function)} and {@link IsNotIn#filter(Predicate)}
     * @param valueStreamTransformer a UnaryOperator that will transform the value stream before
     *     the values are placed in the parameter map
     * @return new condition with the specified transformer
     */
    @Deprecated
    public IsNotIn<T> then(UnaryOperator<Stream<T>> valueStreamTransformer) {
        List<T> mapped = valueStreamTransformer.apply(values.stream())
                .collect(Collectors.toList());

        return new IsNotIn<>(mapped, emptyCallback);
    }

    @Override
    public IsNotIn<T> filter(Predicate<? super T> predicate) {
        return filterSupport(predicate, IsNotIn::new, this, IsNotIn::empty);
    }

    /**
     * If renderable, apply the mapping to each value in the list return a new condition with the mapped values.
     *     Else return a condition that will not render (this).
     *
     * @param mapper a mapping function to apply to the values, if renderable
     * @param <R> type of the new condition
     * @return a new condition with mapped values if renderable, otherwise a condition
     *     that will not render.
     */
    public <R> IsNotIn<R> map(Function<? super T, ? extends R> mapper) {
        BiFunction<Collection<R>, Callback, IsNotIn<R>> constructor = IsNotIn::new;
        return mapSupport(mapper, constructor, IsNotIn::empty);
    }

    @SafeVarargs
    public static <T> IsNotIn<T> of(T... values) {
        return of(Arrays.asList(values));
    }

    public static <T> IsNotIn<T> of(Collection<T> values) {
        return new IsNotIn<>(values);
    }
}
