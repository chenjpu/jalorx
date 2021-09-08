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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import io.jalorx.boot.sql.dsl.BasicColumn;
import io.jalorx.boot.sql.dsl.SqlTable;
import io.jalorx.boot.sql.dsl.TableExpression;
import io.jalorx.boot.sql.dsl.render.GuaranteedTableAliasCalculator;
import io.jalorx.boot.sql.dsl.render.TableAliasCalculator;
import io.jalorx.boot.sql.dsl.select.join.JoinModel;
import io.jalorx.boot.sql.dsl.where.WhereModel;

public class QueryExpressionModel {
    private final String connector;
    private final boolean isDistinct;
    private final List<BasicColumn> selectList;
    private final TableExpression table;
    private final JoinModel joinModel;
    private final TableAliasCalculator tableAliasCalculator;
    private final WhereModel whereModel;
    private final GroupByModel groupByModel;

    private QueryExpressionModel(Builder builder) {
        connector = builder.connector;
        isDistinct = builder.isDistinct;
        selectList = Objects.requireNonNull(builder.selectList);
        table = Objects.requireNonNull(builder.table);
        joinModel = builder.joinModel;
        tableAliasCalculator = joinModel().map(jm -> determineJoinTableAliasCalculator(jm, builder.tableAliases))
                .orElseGet(() -> TableAliasCalculator.of(builder.tableAliases));
        whereModel = builder.whereModel;
        groupByModel = builder.groupByModel;
    }

    private TableAliasCalculator determineJoinTableAliasCalculator(JoinModel joinModel, Map<SqlTable,
            String> tableAliases) {
        if (joinModel.containsSubQueries()) {
            // if there are subQueries, then force explicit qualifiers
            return TableAliasCalculator.of(tableAliases);
        } else {
            return GuaranteedTableAliasCalculator.of(tableAliases);
        }
    }

    public Optional<String> connector() {
        return Optional.ofNullable(connector);
    }

    public boolean isDistinct() {
        return isDistinct;
    }

    public <R> Stream<R> mapColumns(Function<BasicColumn, R> mapper) {
        return selectList.stream().map(mapper);
    }

    public TableExpression table() {
        return table;
    }

    public TableAliasCalculator tableAliasCalculator() {
        return tableAliasCalculator;
    }

    public Optional<WhereModel> whereModel() {
        return Optional.ofNullable(whereModel);
    }

    public Optional<JoinModel> joinModel() {
        return Optional.ofNullable(joinModel);
    }

    public Optional<GroupByModel> groupByModel() {
        return Optional.ofNullable(groupByModel);
    }

    public static Builder withSelectList(List<BasicColumn> columnList) {
        return new Builder().withSelectList(columnList);
    }

    public static class Builder {
        private String connector;
        private boolean isDistinct;
        private final List<BasicColumn> selectList = new ArrayList<>();
        private TableExpression table;
        private final Map<SqlTable, String> tableAliases = new HashMap<>();
        private WhereModel whereModel;
        private JoinModel joinModel;
        private GroupByModel groupByModel;

        public Builder withConnector(String connector) {
            this.connector = connector;
            return this;
        }

        public Builder withTable(TableExpression table) {
            this.table = table;
            return this;
        }

        public Builder isDistinct(boolean isDistinct) {
            this.isDistinct = isDistinct;
            return this;
        }

        public Builder withSelectColumn(BasicColumn selectColumn) {
            this.selectList.add(selectColumn);
            return this;
        }

        public Builder withSelectList(List<BasicColumn> selectList) {
            this.selectList.addAll(selectList);
            return this;
        }

        public Builder withTableAliases(Map<SqlTable, String> tableAliases) {
            this.tableAliases.putAll(tableAliases);
            return this;
        }

        public Builder withWhereModel(WhereModel whereModel) {
            this.whereModel = whereModel;
            return this;
        }

        public Builder withJoinModel(JoinModel joinModel) {
            this.joinModel = joinModel;
            return this;
        }

        public Builder withGroupByModel(GroupByModel groupByModel) {
            this.groupByModel = groupByModel;
            return this;
        }

        public QueryExpressionModel build() {
            return new QueryExpressionModel(this);
        }
    }
}
