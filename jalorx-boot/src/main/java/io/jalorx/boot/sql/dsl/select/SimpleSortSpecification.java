/*
 *    Copyright 2016-2020 the original author or authors.
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
package io.jalorx.boot.sql.dsl.select;

import java.util.Objects;

import io.jalorx.boot.sql.dsl.SortSpecification;

/**
 * This class is used for an order by phrase where there is no suitable column name
 * to use (for example a calculated column or an aggregate column).
 *
 * @author Jeff Butler
 */
public class SimpleSortSpecification implements SortSpecification {

    private final String name;
    private final boolean isDescending;

    private SimpleSortSpecification(String name) {
        this(name, false);
    }

    private SimpleSortSpecification(String name, boolean isDescending) {
        this.name = Objects.requireNonNull(name);
        this.isDescending = isDescending;
    }

    @Override
    public SortSpecification descending() {
        return new SimpleSortSpecification(name, true);
    }

    @Override
    public String orderByName() {
        return name;
    }

    @Override
    public boolean isDescending() {
        return isDescending;
    }

    public static SimpleSortSpecification of(String name) {
        return new SimpleSortSpecification(name);
    }
}
