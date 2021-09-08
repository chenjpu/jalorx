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

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import io.jalorx.boot.sql.dsl.select.ColumnSortSpecification;
import io.jalorx.boot.sql.dsl.select.CountDSL;
import io.jalorx.boot.sql.dsl.select.QueryExpressionDSL.FromGatherer;
import io.jalorx.boot.sql.dsl.select.SelectDSL;
import io.jalorx.boot.sql.dsl.select.SelectModel;
import io.jalorx.boot.sql.dsl.select.SimpleSortSpecification;
import io.jalorx.boot.sql.dsl.select.aggregate.Avg;
import io.jalorx.boot.sql.dsl.select.aggregate.Count;
import io.jalorx.boot.sql.dsl.select.aggregate.CountAll;
import io.jalorx.boot.sql.dsl.select.aggregate.CountDistinct;
import io.jalorx.boot.sql.dsl.select.aggregate.Max;
import io.jalorx.boot.sql.dsl.select.aggregate.Min;
import io.jalorx.boot.sql.dsl.select.aggregate.Sum;
import io.jalorx.boot.sql.dsl.select.function.Add;
import io.jalorx.boot.sql.dsl.select.function.Concatenate;
import io.jalorx.boot.sql.dsl.select.function.Divide;
import io.jalorx.boot.sql.dsl.select.function.Lower;
import io.jalorx.boot.sql.dsl.select.function.Multiply;
import io.jalorx.boot.sql.dsl.select.function.OperatorFunction;
import io.jalorx.boot.sql.dsl.select.function.Substring;
import io.jalorx.boot.sql.dsl.select.function.Subtract;
import io.jalorx.boot.sql.dsl.select.function.Upper;
import io.jalorx.boot.sql.dsl.select.join.EqualTo;
import io.jalorx.boot.sql.dsl.select.join.JoinCondition;
import io.jalorx.boot.sql.dsl.select.join.JoinCriterion;
import io.jalorx.boot.sql.dsl.util.Buildable;
import io.jalorx.boot.sql.dsl.where.WhereDSL;
import io.jalorx.boot.sql.dsl.where.condition.IsBetween;
import io.jalorx.boot.sql.dsl.where.condition.IsEqualTo;
import io.jalorx.boot.sql.dsl.where.condition.IsEqualToColumn;
import io.jalorx.boot.sql.dsl.where.condition.IsEqualToWithSubselect;
import io.jalorx.boot.sql.dsl.where.condition.IsGreaterThan;
import io.jalorx.boot.sql.dsl.where.condition.IsGreaterThanColumn;
import io.jalorx.boot.sql.dsl.where.condition.IsGreaterThanOrEqualTo;
import io.jalorx.boot.sql.dsl.where.condition.IsGreaterThanOrEqualToColumn;
import io.jalorx.boot.sql.dsl.where.condition.IsGreaterThanOrEqualToWithSubselect;
import io.jalorx.boot.sql.dsl.where.condition.IsGreaterThanWithSubselect;
import io.jalorx.boot.sql.dsl.where.condition.IsIn;
import io.jalorx.boot.sql.dsl.where.condition.IsInCaseInsensitive;
import io.jalorx.boot.sql.dsl.where.condition.IsInWithSubselect;
import io.jalorx.boot.sql.dsl.where.condition.IsLessThan;
import io.jalorx.boot.sql.dsl.where.condition.IsLessThanColumn;
import io.jalorx.boot.sql.dsl.where.condition.IsLessThanOrEqualTo;
import io.jalorx.boot.sql.dsl.where.condition.IsLessThanOrEqualToColumn;
import io.jalorx.boot.sql.dsl.where.condition.IsLessThanOrEqualToWithSubselect;
import io.jalorx.boot.sql.dsl.where.condition.IsLessThanWithSubselect;
import io.jalorx.boot.sql.dsl.where.condition.IsLike;
import io.jalorx.boot.sql.dsl.where.condition.IsLikeCaseInsensitive;
import io.jalorx.boot.sql.dsl.where.condition.IsNotBetween;
import io.jalorx.boot.sql.dsl.where.condition.IsNotEqualTo;
import io.jalorx.boot.sql.dsl.where.condition.IsNotEqualToColumn;
import io.jalorx.boot.sql.dsl.where.condition.IsNotEqualToWithSubselect;
import io.jalorx.boot.sql.dsl.where.condition.IsNotIn;
import io.jalorx.boot.sql.dsl.where.condition.IsNotInCaseInsensitive;
import io.jalorx.boot.sql.dsl.where.condition.IsNotInWithSubselect;
import io.jalorx.boot.sql.dsl.where.condition.IsNotLike;
import io.jalorx.boot.sql.dsl.where.condition.IsNotLikeCaseInsensitive;
import io.jalorx.boot.sql.dsl.where.condition.IsNotNull;
import io.jalorx.boot.sql.dsl.where.condition.IsNull;

public interface SqlBuilder {

    // statements

    /**
     * Renders as select count(distinct column) from table...
     *
     * @param column the column to count
     * @return the next step in the DSL
     */
    static CountDSL.FromGatherer<SelectModel> countDistinctColumn(BasicColumn column) {
        return CountDSL.countDistinct(column);
    }

    /**
     * Renders as select count(column) from table...
     *
     * @param column the column to count
     * @return the next step in the DSL
     */
    static CountDSL.FromGatherer<SelectModel> countColumn(BasicColumn column) {
        return CountDSL.count(column);
    }

    /**
     * Renders as select count(*) from table...
     *
     * @param table the table to count
     * @return the next step in the DSL
     */
    static CountDSL<SelectModel> countFrom(SqlTable table) {
        return CountDSL.countFrom(table);
    }

    static FromGatherer<SelectModel> select(BasicColumn...selectList) {
        return SelectDSL.select(selectList);
    }

    static FromGatherer<SelectModel> select(Collection<BasicColumn> selectList) {
        return SelectDSL.select(selectList);
    }

    static FromGatherer<SelectModel> selectDistinct(BasicColumn...selectList) {
        return SelectDSL.selectDistinct(selectList);
    }

    static FromGatherer<SelectModel> selectDistinct(Collection<BasicColumn> selectList) {
        return SelectDSL.selectDistinct(selectList);
    }


    static WhereDSL where() {
        return WhereDSL.where();
    }

    static <T> WhereDSL where(BindableColumn<T> column, VisitableCondition<T> condition) {
        return WhereDSL.where().where(column, condition);
    }

    static <T> WhereDSL where(BindableColumn<T> column, VisitableCondition<T> condition,
            SqlCriterion... subCriteria) {
        return WhereDSL.where().where(column, condition, subCriteria);
    }

    static WhereDSL where(ExistsPredicate existsPredicate) {
        return WhereDSL.where().where(existsPredicate);
    }

    static WhereDSL where(ExistsPredicate existsPredicate, SqlCriterion... subCriteria) {
        return WhereDSL.where().where(existsPredicate, subCriteria);
    }

    // where condition connectors
    static <T> SqlCriterion or(BindableColumn<T> column, VisitableCondition<T> condition) {
        return ColumnAndConditionCriterion.withColumn(column)
                .withConnector("or") //$NON-NLS-1$
                .withCondition(condition)
                .build();
    }

    static <T> SqlCriterion or(BindableColumn<T> column, VisitableCondition<T> condition,
            SqlCriterion...subCriteria) {
        return ColumnAndConditionCriterion.withColumn(column)
                .withConnector("or") //$NON-NLS-1$
                .withCondition(condition)
                .withSubCriteria(Arrays.asList(subCriteria))
                .build();
    }

    static SqlCriterion or(ExistsPredicate existsPredicate) {
        return new ExistsCriterion.Builder()
                .withConnector("or") //$NON-NLS-1$
                .withExistsPredicate(existsPredicate)
                .build();
    }

    static SqlCriterion or(ExistsPredicate existsPredicate, SqlCriterion...subCriteria) {
        return new ExistsCriterion.Builder()
                .withConnector("or") //$NON-NLS-1$
                .withExistsPredicate(existsPredicate)
                .withSubCriteria(Arrays.asList(subCriteria))
                .build();
    }

    static <T> SqlCriterion and(BindableColumn<T> column, VisitableCondition<T> condition) {
        return ColumnAndConditionCriterion.withColumn(column)
                .withConnector("and") //$NON-NLS-1$
                .withCondition(condition)
                .build();
    }

    static <T> SqlCriterion and(BindableColumn<T> column, VisitableCondition<T> condition,
            SqlCriterion...subCriteria) {
        return ColumnAndConditionCriterion.withColumn(column)
                .withConnector("and") //$NON-NLS-1$
                .withCondition(condition)
                .withSubCriteria(Arrays.asList(subCriteria))
                .build();
    }

    static SqlCriterion and(ExistsPredicate existsPredicate) {
        return new ExistsCriterion.Builder()
                .withConnector("and") //$NON-NLS-1$
                .withExistsPredicate(existsPredicate)
                .build();
    }

    static SqlCriterion and(ExistsPredicate existsPredicate, SqlCriterion...subCriteria) {
        return new ExistsCriterion.Builder()
                .withConnector("and") //$NON-NLS-1$
                .withExistsPredicate(existsPredicate)
                .withSubCriteria(Arrays.asList(subCriteria))
                .build();
    }

    // join support
    static JoinCriterion and(BasicColumn joinColumn, JoinCondition joinCondition) {
        return new JoinCriterion.Builder()
                .withConnector("and") //$NON-NLS-1$
                .withJoinColumn(joinColumn)
                .withJoinCondition(joinCondition)
                .build();
    }

    static JoinCriterion on(BasicColumn joinColumn, JoinCondition joinCondition) {
        return new JoinCriterion.Builder()
                .withConnector("on") //$NON-NLS-1$
                .withJoinColumn(joinColumn)
                .withJoinCondition(joinCondition)
                .build();
    }

    static EqualTo equalTo(BasicColumn column) {
        return new EqualTo(column);
    }

    // aggregate support
    static CountAll count() {
        return new CountAll();
    }

    static Count count(BasicColumn column) {
        return Count.of(column);
    }

    static CountDistinct countDistinct(BasicColumn column) {
        return CountDistinct.of(column);
    }

    static <T> Max<T> max(BindableColumn<T> column) {
        return Max.of(column);
    }

    static <T> Min<T> min(BindableColumn<T> column) {
        return Min.of(column);
    }

    static <T> Avg<T> avg(BindableColumn<T> column) {
        return Avg.of(column);
    }

    static <T> Sum<T> sum(BindableColumn<T> column) {
        return Sum.of(column);
    }

    // constants
    static <T> Constant<T> constant(String constant) {
        return Constant.of(constant);
    }

    static StringConstant stringConstant(String constant) {
        return StringConstant.of(constant);
    }

    // functions
    static <T> Add<T> add(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            BasicColumn... subsequentColumns) {
        return Add.of(firstColumn, secondColumn, subsequentColumns);
    }

    static <T> Divide<T> divide(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            BasicColumn... subsequentColumns) {
        return Divide.of(firstColumn, secondColumn, subsequentColumns);
    }

    static <T> Multiply<T> multiply(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            BasicColumn... subsequentColumns) {
        return Multiply.of(firstColumn, secondColumn, subsequentColumns);
    }

    static <T> Subtract<T> subtract(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            BasicColumn... subsequentColumns) {
        return Subtract.of(firstColumn, secondColumn, subsequentColumns);
    }

    static <T> Concatenate<T> concatenate(BindableColumn<T> firstColumn, BasicColumn secondColumn,
            BasicColumn... subsequentColumns) {
        return Concatenate.concatenate(firstColumn, secondColumn, subsequentColumns);
    }

    static <T> OperatorFunction<T> applyOperator(String operator, BindableColumn<T> firstColumn,
            BasicColumn secondColumn, BasicColumn... subsequentColumns) {
        return OperatorFunction.of(operator, firstColumn, secondColumn, subsequentColumns);
    }

    static <T> Lower<T> lower(BindableColumn<T> column) {
        return Lower.of(column);
    }

    static <T> Substring<T> substring(BindableColumn<T> column, int offset, int length) {
        return Substring.of(column, offset, length);
    }

    static <T> Upper<T> upper(BindableColumn<T> column) {
        return Upper.of(column);
    }

    // conditions for all data types
    static ExistsPredicate exists(Buildable<SelectModel> selectModelBuilder) {
        return ExistsPredicate.exists(selectModelBuilder);
    }

    static ExistsPredicate notExists(Buildable<SelectModel> selectModelBuilder) {
        return ExistsPredicate.notExists(selectModelBuilder);
    }

    static <T> IsNull<T> isNull() {
        return new IsNull<>();
    }

    static <T> IsNotNull<T> isNotNull() {
        return new IsNotNull<>();
    }

    static <T> IsEqualTo<T> isEqualTo(T value) {
        return IsEqualTo.of(value);
    }

    static <T> IsEqualTo<T> isEqualTo(Supplier<T> valueSupplier) {
        return isEqualTo(valueSupplier.get());
    }

    static <T> IsEqualToWithSubselect<T> isEqualTo(Buildable<SelectModel> selectModelBuilder) {
        return IsEqualToWithSubselect.of(selectModelBuilder);
    }

    static <T> IsEqualToColumn<T> isEqualTo(BasicColumn column) {
        return IsEqualToColumn.of(column);
    }

    static <T> IsEqualTo<T> isEqualToWhenPresent(T value) {
        return IsEqualTo.of(value).filter(Objects::nonNull);
    }

    static <T> IsEqualTo<T> isEqualToWhenPresent(Supplier<T> valueSupplier) {
        return isEqualToWhenPresent(valueSupplier.get());
    }

    static <T> IsNotEqualTo<T> isNotEqualTo(T value) {
        return IsNotEqualTo.of(value);
    }

    static <T> IsNotEqualTo<T> isNotEqualTo(Supplier<T> valueSupplier) {
        return isNotEqualTo(valueSupplier.get());
    }

    static <T> IsNotEqualToWithSubselect<T> isNotEqualTo(Buildable<SelectModel> selectModelBuilder) {
        return IsNotEqualToWithSubselect.of(selectModelBuilder);
    }

    static <T> IsNotEqualToColumn<T> isNotEqualTo(BasicColumn column) {
        return IsNotEqualToColumn.of(column);
    }

    static <T> IsNotEqualTo<T> isNotEqualToWhenPresent(T value) {
        return IsNotEqualTo.of(value).filter(Objects::nonNull);
    }

    static <T> IsNotEqualTo<T> isNotEqualToWhenPresent(Supplier<T> valueSupplier) {
        return isNotEqualToWhenPresent(valueSupplier.get());
    }

    static <T> IsGreaterThan<T> isGreaterThan(T value) {
        return IsGreaterThan.of(value);
    }

    static <T> IsGreaterThan<T> isGreaterThan(Supplier<T> valueSupplier) {
        return isGreaterThan(valueSupplier.get());
    }

    static <T> IsGreaterThanWithSubselect<T> isGreaterThan(Buildable<SelectModel> selectModelBuilder) {
        return IsGreaterThanWithSubselect.of(selectModelBuilder);
    }

    static <T> IsGreaterThanColumn<T> isGreaterThan(BasicColumn column) {
        return IsGreaterThanColumn.of(column);
    }

    static <T> IsGreaterThan<T> isGreaterThanWhenPresent(T value) {
        return IsGreaterThan.of(value).filter(Objects::nonNull);
    }

    static <T> IsGreaterThan<T> isGreaterThanWhenPresent(Supplier<T> valueSupplier) {
        return isGreaterThanWhenPresent(valueSupplier.get());
    }

    static <T> IsGreaterThanOrEqualTo<T> isGreaterThanOrEqualTo(T value) {
        return IsGreaterThanOrEqualTo.of(value);
    }

    static <T> IsGreaterThanOrEqualTo<T> isGreaterThanOrEqualTo(Supplier<T> valueSupplier) {
        return isGreaterThanOrEqualTo(valueSupplier.get());
    }

    static <T> IsGreaterThanOrEqualToWithSubselect<T> isGreaterThanOrEqualTo(
            Buildable<SelectModel> selectModelBuilder) {
        return IsGreaterThanOrEqualToWithSubselect.of(selectModelBuilder);
    }

    static <T> IsGreaterThanOrEqualToColumn<T> isGreaterThanOrEqualTo(BasicColumn column) {
        return IsGreaterThanOrEqualToColumn.of(column);
    }

    static <T> IsGreaterThanOrEqualTo<T> isGreaterThanOrEqualToWhenPresent(T value) {
        return IsGreaterThanOrEqualTo.of(value).filter(Objects::nonNull);
    }

    static <T> IsGreaterThanOrEqualTo<T> isGreaterThanOrEqualToWhenPresent(Supplier<T> valueSupplier) {
        return isGreaterThanOrEqualToWhenPresent(valueSupplier.get());
    }

    static <T> IsLessThan<T> isLessThan(T value) {
        return IsLessThan.of(value);
    }

    static <T> IsLessThan<T> isLessThan(Supplier<T> valueSupplier) {
        return isLessThan(valueSupplier.get());
    }

    static <T> IsLessThanWithSubselect<T> isLessThan(Buildable<SelectModel> selectModelBuilder) {
        return IsLessThanWithSubselect.of(selectModelBuilder);
    }

    static <T> IsLessThanColumn<T> isLessThan(BasicColumn column) {
        return IsLessThanColumn.of(column);
    }

    static <T> IsLessThan<T> isLessThanWhenPresent(T value) {
        return IsLessThan.of(value).filter(Objects::nonNull);
    }

    static <T> IsLessThan<T> isLessThanWhenPresent(Supplier<T> valueSupplier) {
        return isLessThanWhenPresent(valueSupplier.get());
    }

    static <T> IsLessThanOrEqualTo<T> isLessThanOrEqualTo(T value) {
        return IsLessThanOrEqualTo.of(value);
    }

    static <T> IsLessThanOrEqualTo<T> isLessThanOrEqualTo(Supplier<T> valueSupplier) {
        return isLessThanOrEqualTo(valueSupplier.get());
    }

    static <T> IsLessThanOrEqualToWithSubselect<T> isLessThanOrEqualTo(Buildable<SelectModel> selectModelBuilder) {
        return IsLessThanOrEqualToWithSubselect.of(selectModelBuilder);
    }

    static <T> IsLessThanOrEqualToColumn<T> isLessThanOrEqualTo(BasicColumn column) {
        return IsLessThanOrEqualToColumn.of(column);
    }

    static <T> IsLessThanOrEqualTo<T> isLessThanOrEqualToWhenPresent(T value) {
        return IsLessThanOrEqualTo.of(value).filter(Objects::nonNull);
    }

    static <T> IsLessThanOrEqualTo<T> isLessThanOrEqualToWhenPresent(Supplier<T> valueSupplier) {
        return isLessThanOrEqualToWhenPresent(valueSupplier.get());
    }

    @SafeVarargs
    static <T> IsIn<T> isIn(T...values) {
        return IsIn.of(values);
    }

    static <T> IsIn<T> isIn(Collection<T> values) {
        return IsIn.of(values);
    }

    static <T> IsInWithSubselect<T> isIn(Buildable<SelectModel> selectModelBuilder) {
        return IsInWithSubselect.of(selectModelBuilder);
    }

    @SafeVarargs
    static <T> IsIn<T> isInWhenPresent(T...values) {
        return IsIn.of(values).filter(Objects::nonNull);
    }

    static <T> IsIn<T> isInWhenPresent(Collection<T> values) {
        return values == null ? IsIn.empty() : IsIn.of(values).filter(Objects::nonNull);
    }

    @SafeVarargs
    static <T> IsNotIn<T> isNotIn(T...values) {
        return IsNotIn.of(values);
    }

    static <T> IsNotIn<T> isNotIn(Collection<T> values) {
        return IsNotIn.of(values);
    }

    static <T> IsNotInWithSubselect<T> isNotIn(Buildable<SelectModel> selectModelBuilder) {
        return IsNotInWithSubselect.of(selectModelBuilder);
    }

    @SafeVarargs
    static <T> IsNotIn<T> isNotInWhenPresent(T...values) {
        return IsNotIn.of(values).filter(Objects::nonNull);
    }

    static <T> IsNotIn<T> isNotInWhenPresent(Collection<T> values) {
        return values == null ? IsNotIn.empty() : IsNotIn.of(values).filter(Objects::nonNull);
    }

    static <T> IsBetween.Builder<T> isBetween(T value1) {
        return IsBetween.isBetween(value1);
    }

    static <T> IsBetween.Builder<T> isBetween(Supplier<T> valueSupplier1) {
        return isBetween(valueSupplier1.get());
    }

    static <T> IsBetween.WhenPresentBuilder<T> isBetweenWhenPresent(T value1) {
        return IsBetween.isBetweenWhenPresent(value1);
    }

    static <T> IsBetween.WhenPresentBuilder<T> isBetweenWhenPresent(Supplier<T> valueSupplier1) {
        return isBetweenWhenPresent(valueSupplier1.get());
    }

    static <T> IsNotBetween.Builder<T> isNotBetween(T value1) {
        return IsNotBetween.isNotBetween(value1);
    }

    static <T> IsNotBetween.Builder<T> isNotBetween(Supplier<T> valueSupplier1) {
        return isNotBetween(valueSupplier1.get());
    }

    static <T> IsNotBetween.WhenPresentBuilder<T> isNotBetweenWhenPresent(T value1) {
        return IsNotBetween.isNotBetweenWhenPresent(value1);
    }

    static <T> IsNotBetween.WhenPresentBuilder<T> isNotBetweenWhenPresent(Supplier<T> valueSupplier1) {
        return isNotBetweenWhenPresent(valueSupplier1.get());
    }

    // for string columns, but generic for columns with type handlers
    static <T> IsLike<T> isLike(T value) {
        return IsLike.of(value);
    }

    static <T> IsLike<T> isLike(Supplier<T> valueSupplier) {
        return isLike(valueSupplier.get());
    }

    static <T> IsLike<T> isLikeWhenPresent(T value) {
        return IsLike.of(value).filter(Objects::nonNull);
    }

    static <T> IsLike<T> isLikeWhenPresent(Supplier<T> valueSupplier) {
        return isLikeWhenPresent(valueSupplier.get());
    }

    static <T> IsNotLike<T> isNotLike(T value) {
        return IsNotLike.of(value);
    }

    static <T> IsNotLike<T> isNotLike(Supplier<T> valueSupplier) {
        return isNotLike(valueSupplier.get());
    }

    static <T> IsNotLike<T> isNotLikeWhenPresent(T value) {
        return IsNotLike.of(value).filter(Objects::nonNull);
    }

    static <T> IsNotLike<T> isNotLikeWhenPresent(Supplier<T> valueSupplier) {
        return isNotLikeWhenPresent(valueSupplier.get());
    }

    // shortcuts for booleans
    static IsEqualTo<Boolean> isTrue() {
        return isEqualTo(Boolean.TRUE);
    }

    static IsEqualTo<Boolean> isFalse() {
        return isEqualTo(Boolean.FALSE);
    }

    // conditions for strings only
    static IsLikeCaseInsensitive isLikeCaseInsensitive(String value) {
        return IsLikeCaseInsensitive.of(value);
    }

    static IsLikeCaseInsensitive isLikeCaseInsensitive(Supplier<String> valueSupplier) {
        return isLikeCaseInsensitive(valueSupplier.get());
    }

    static IsLikeCaseInsensitive isLikeCaseInsensitiveWhenPresent(String value) {
        return IsLikeCaseInsensitive.of(value).filter(Objects::nonNull);
    }

    static IsLikeCaseInsensitive isLikeCaseInsensitiveWhenPresent(Supplier<String> valueSupplier) {
        return isLikeCaseInsensitiveWhenPresent(valueSupplier.get());
    }

    static IsNotLikeCaseInsensitive isNotLikeCaseInsensitive(String value) {
        return IsNotLikeCaseInsensitive.of(value);
    }

    static IsNotLikeCaseInsensitive isNotLikeCaseInsensitive(Supplier<String> valueSupplier) {
        return isNotLikeCaseInsensitive(valueSupplier.get());
    }

    static IsNotLikeCaseInsensitive isNotLikeCaseInsensitiveWhenPresent(String value) {
        return IsNotLikeCaseInsensitive.of(value).filter(Objects::nonNull);
    }

    static IsNotLikeCaseInsensitive isNotLikeCaseInsensitiveWhenPresent(Supplier<String> valueSupplier) {
        return isNotLikeCaseInsensitiveWhenPresent(valueSupplier.get());
    }

    static IsInCaseInsensitive isInCaseInsensitive(String...values) {
        return IsInCaseInsensitive.of(values);
    }

    static IsInCaseInsensitive isInCaseInsensitive(Collection<String> values) {
        return IsInCaseInsensitive.of(values);
    }

    static IsInCaseInsensitive isInCaseInsensitiveWhenPresent(String...values) {
        return IsInCaseInsensitive.of(values).filter(Objects::nonNull);
    }

    static IsInCaseInsensitive isInCaseInsensitiveWhenPresent(Collection<String> values) {
        return values == null ? IsInCaseInsensitive.empty() : IsInCaseInsensitive.of(values).filter(Objects::nonNull);
    }

    static IsNotInCaseInsensitive isNotInCaseInsensitive(String...values) {
        return IsNotInCaseInsensitive.of(values);
    }

    static IsNotInCaseInsensitive isNotInCaseInsensitive(Collection<String> values) {
        return IsNotInCaseInsensitive.of(values);
    }

    static IsNotInCaseInsensitive isNotInCaseInsensitiveWhenPresent(String...values) {
        return IsNotInCaseInsensitive.of(values).filter(Objects::nonNull);
    }

    static IsNotInCaseInsensitive isNotInCaseInsensitiveWhenPresent(Collection<String> values) {
        return values == null ? IsNotInCaseInsensitive.empty() :
                IsNotInCaseInsensitive.of(values).filter(Objects::nonNull);
    }

    // order by support

    /**
     * Creates a sort specification based on a String. This is useful when a column has been
     * aliased in the select list. For example:
     *
     * <pre>
     *     select(foo.as("bar"))
     *     .from(baz)
     *     .orderBy(sortColumn("bar"))
     * </pre>
     *
     * @param name the string to use as a sort specification
     * @return a sort specification
     */
    static SortSpecification sortColumn(String name) {
        return SimpleSortSpecification.of(name);
    }

    /**
     * Creates a sort specification based on a column and a table alias. This can be useful in a join
     * where the desired sort order is based on a column not in the select list. This will likely
     * fail in union queries depending on database support.
     *
     * @param tableAlias the table alias
     * @param column the column
     * @return a sort specification
     */
    static SortSpecification sortColumn(String tableAlias, SqlColumn<?> column) {
        return new ColumnSortSpecification(tableAlias, column);
    }

}
