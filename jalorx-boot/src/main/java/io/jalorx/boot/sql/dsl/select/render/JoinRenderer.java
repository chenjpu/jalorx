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
package io.jalorx.boot.sql.dsl.select.render;

import static io.jalorx.boot.sql.dsl.util.StringUtilities.spaceAfter;
import static io.jalorx.boot.sql.dsl.util.StringUtilities.spaceBefore;

import java.util.Objects;
import java.util.stream.Collectors;

import io.jalorx.boot.sql.dsl.BasicColumn;
import io.jalorx.boot.sql.dsl.select.QueryExpressionModel;
import io.jalorx.boot.sql.dsl.select.join.JoinCriterion;
import io.jalorx.boot.sql.dsl.select.join.JoinModel;
import io.jalorx.boot.sql.dsl.select.join.JoinSpecification;
import io.jalorx.boot.sql.dsl.util.FragmentAndParameters;
import io.jalorx.boot.sql.dsl.util.FragmentCollector;

public class JoinRenderer {
    private final JoinModel joinModel;
    private final QueryExpressionModel queryExpression;
    private final TableExpressionRenderer tableExpressionRenderer;

    private JoinRenderer(Builder builder) {
        joinModel = Objects.requireNonNull(builder.joinModel);
        queryExpression = Objects.requireNonNull(builder.queryExpression);
        tableExpressionRenderer = Objects.requireNonNull(builder.tableExpressionRenderer);
    }

    public FragmentAndParameters render() {
        FragmentCollector fc = joinModel.mapJoinSpecifications(this::renderJoinSpecification)
                .collect(FragmentCollector.collect());

        return FragmentAndParameters.withFragment(fc.fragments().collect(Collectors.joining(" "))) //$NON-NLS-1$
                .withParameters(fc.parameters())
                .build();
    }

    private FragmentAndParameters renderJoinSpecification(JoinSpecification joinSpecification) {
        FragmentAndParameters renderedTable = joinSpecification.table().accept(tableExpressionRenderer);

        String fragment = spaceAfter(joinSpecification.joinType().shortType())
                + "join" //$NON-NLS-1$
                + spaceBefore(renderedTable.fragment())
                + spaceBefore(renderConditions(joinSpecification));

        return FragmentAndParameters.withFragment(fragment)
                .withParameters(renderedTable.parameters())
                .build();
    }

    private String renderConditions(JoinSpecification joinSpecification) {
        return joinSpecification.mapJoinCriteria(this::renderCriterion)
                .collect(Collectors.joining(" ")); //$NON-NLS-1$
    }

    private String renderCriterion(JoinCriterion joinCriterion) {
        return joinCriterion.connector()
                + spaceBefore(applyTableAlias(joinCriterion.leftColumn()))
                + spaceBefore(joinCriterion.operator())
                + spaceBefore(applyTableAlias(joinCriterion.rightColumn()));
    }

    private String applyTableAlias(BasicColumn column) {
        return column.renderWithTableAlias(queryExpression.tableAliasCalculator());
    }

    public static Builder withJoinModel(JoinModel joinModel) {
        return new Builder().withJoinModel(joinModel);
    }

    public static class Builder {
        private JoinModel joinModel;
        private QueryExpressionModel queryExpression;
        private TableExpressionRenderer tableExpressionRenderer;

        public Builder withJoinModel(JoinModel joinModel) {
            this.joinModel = joinModel;
            return this;
        }

        public Builder withQueryExpression(QueryExpressionModel queryExpression) {
            this.queryExpression = queryExpression;
            return this;
        }

        public Builder withTableExpressionRenderer(TableExpressionRenderer tableExpressionRenderer) {
            this.tableExpressionRenderer = tableExpressionRenderer;
            return this;
        }

        public JoinRenderer build() {
            return new JoinRenderer(this);
        }
    }
}
