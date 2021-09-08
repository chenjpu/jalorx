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
package io.jalorx.boot.sql.dsl.util;

import io.jalorx.boot.sql.dsl.BasicColumn;
import io.jalorx.boot.sql.dsl.SqlColumn;

public class ColumnToColumnMapping extends AbstractColumnMapping {

    private final BasicColumn rightColumn;

    private ColumnToColumnMapping(SqlColumn<?> column, BasicColumn rightColumn) {
        super(column);
        this.rightColumn = rightColumn;
    }

    public BasicColumn rightColumn() {
        return rightColumn;
    }

    @Override
    public <R> R accept(ColumnMappingVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public static ColumnToColumnMapping of(SqlColumn<?> column, BasicColumn rightColumn) {
        return new ColumnToColumnMapping(column, rightColumn);
    }
}
