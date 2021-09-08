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
package io.jalorx.boot.sql.dsl.where.condition;

import io.jalorx.boot.sql.dsl.AbstractColumnComparisonCondition;
import io.jalorx.boot.sql.dsl.BasicColumn;

public class IsEqualToColumn<T> extends AbstractColumnComparisonCondition<T> {

    protected IsEqualToColumn(BasicColumn column) {
        super(column);
    }

    @Override
    protected String renderCondition(String leftColumn, String rightColumn) {
        return leftColumn + " = " + rightColumn; //$NON-NLS-1$
    }

    public static <T> IsEqualToColumn<T> of(BasicColumn column) {
        return new IsEqualToColumn<>(column);
    }
}