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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import io.jalorx.boot.sql.dsl.BasicColumn;
import io.jalorx.boot.sql.dsl.SortSpecification;
import io.jalorx.boot.sql.dsl.SqlTable;
import io.jalorx.boot.sql.dsl.TableExpression;
import io.jalorx.boot.sql.dsl.select.QueryExpressionDSL.FromGatherer;
import io.jalorx.boot.sql.dsl.util.Buildable;

/**
 * Implements a SQL DSL for building select statements.
 *
 * @author Jeff Butler
 *
 * @param <R> the type of model produced by this builder, typically SelectModel
 */
public class SelectDSL<R> implements Buildable<R> {

    private final Function<SelectModel, R> adapterFunction;
    private final List<QueryExpressionDSL<R>> queryExpressions = new ArrayList<>();
    private OrderByModel orderByModel;
    private Long limit;
    private Long offset;
    private Long fetchFirstRows;

    private SelectDSL(Function<SelectModel, R> adapterFunction) {
        this.adapterFunction = Objects.requireNonNull(adapterFunction);
    }

    public static QueryExpressionDSL.FromGatherer<SelectModel> select(BasicColumn...selectList) {
        return select(Arrays.asList(selectList));
    }

    public static QueryExpressionDSL.FromGatherer<SelectModel> select(Collection<BasicColumn> selectList) {
        return select(Function.identity(), selectList);
    }

    public static <R> QueryExpressionDSL.FromGatherer<R> select(Function<SelectModel, R> adapterFunction,
            BasicColumn...selectList) {
        return select(adapterFunction, Arrays.asList(selectList));
    }

    public static <R> QueryExpressionDSL.FromGatherer<R> select(Function<SelectModel, R> adapterFunction,
            Collection<BasicColumn> selectList) {
        return new FromGatherer.Builder<R>()
                .withSelectList(selectList)
                .withSelectDSL(new SelectDSL<>(adapterFunction))
                .build();
    }

    public static QueryExpressionDSL.FromGatherer<SelectModel> selectDistinct(BasicColumn...selectList) {
        return selectDistinct(Function.identity(), selectList);
    }

    public static QueryExpressionDSL.FromGatherer<SelectModel> selectDistinct(Collection<BasicColumn> selectList) {
        return selectDistinct(Function.identity(), selectList);
    }

    public static <R> QueryExpressionDSL.FromGatherer<R> selectDistinct(Function<SelectModel, R> adapterFunction,
            BasicColumn...selectList) {
        return selectDistinct(adapterFunction, Arrays.asList(selectList));
    }

    public static <R> QueryExpressionDSL.FromGatherer<R> selectDistinct(Function<SelectModel, R> adapterFunction,
            Collection<BasicColumn> selectList) {
        return new FromGatherer.Builder<R>()
                .withSelectList(selectList)
                .withSelectDSL(new SelectDSL<>(adapterFunction))
                .isDistinct()
                .build();
    }

    QueryExpressionDSL<R> newQueryExpression(FromGatherer<R> fromGatherer, TableExpression table) {
        QueryExpressionDSL<R> queryExpression = new QueryExpressionDSL<>(fromGatherer, table);
        queryExpressions.add(queryExpression);
        return queryExpression;
    }

    QueryExpressionDSL<R> newQueryExpression(FromGatherer<R> fromGatherer, SqlTable table, String tableAlias) {
        QueryExpressionDSL<R> queryExpression = new QueryExpressionDSL<>(fromGatherer, table, tableAlias);
        queryExpressions.add(queryExpression);
        return queryExpression;
    }

    void orderBy(Collection<SortSpecification> columns) {
        orderByModel = OrderByModel.of(columns);
    }

    public LimitFinisher limit(long limit) {
        this.limit = limit;
        return new LimitFinisher();
    }

    public OffsetFirstFinisher offset(long offset) {
        this.offset = offset;
        return new OffsetFirstFinisher();
    }

    public FetchFirstFinisher fetchFirst(long fetchFirstRows) {
        this.fetchFirstRows = fetchFirstRows;
        return new FetchFirstFinisher();
    }

    @NotNull
    @Override
    public R build() {
        SelectModel selectModel = SelectModel.withQueryExpressions(buildModels())
                .withOrderByModel(orderByModel)
                .withPagingModel(buildPagingModel())
                .build();
        return adapterFunction.apply(selectModel);
    }

    private List<QueryExpressionModel> buildModels() {
        return queryExpressions.stream()
                .map(QueryExpressionDSL::buildModel)
                .collect(Collectors.toList());
    }

    private PagingModel buildPagingModel() {
        return new PagingModel.Builder()
                .withLimit(limit)
                .withOffset(offset)
                .withFetchFirstRows(fetchFirstRows)
                .build();
    }

    public class LimitFinisher implements Buildable<R> {
        public OffsetFinisher offset(long offset) {
            SelectDSL.this.offset = offset;
            return new OffsetFinisher();
        }

        @NotNull
        @Override
        public R build() {
            return SelectDSL.this.build();
        }
    }

    public class OffsetFinisher implements Buildable<R> {
        @NotNull
        @Override
        public R build() {
            return SelectDSL.this.build();
        }
    }

    public class OffsetFirstFinisher implements Buildable<R> {
        public FetchFirstFinisher fetchFirst(long fetchFirstRows) {
            SelectDSL.this.fetchFirstRows = fetchFirstRows;
            return new FetchFirstFinisher();
        }

        @NotNull
        @Override
        public R build() {
            return SelectDSL.this.build();
        }
    }

    public class FetchFirstFinisher {
        public RowsOnlyFinisher rowsOnly() {
            return new RowsOnlyFinisher();
        }
    }

    public class RowsOnlyFinisher implements Buildable<R> {
        @NotNull
        @Override
        public R build() {
            return SelectDSL.this.build();
        }
    }
}
