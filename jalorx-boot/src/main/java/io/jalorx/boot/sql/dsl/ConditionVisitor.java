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
package io.jalorx.boot.sql.dsl;

public interface ConditionVisitor<T, R> {
    R visit(AbstractListValueCondition<T> condition);

    R visit(AbstractNoValueCondition<T> condition);

    R visit(AbstractSingleValueCondition<T> condition);

    R visit(AbstractTwoValueCondition<T> condition);

    R visit(AbstractSubselectCondition<T> condition);

    R visit(AbstractColumnComparisonCondition<T> condition);
}
