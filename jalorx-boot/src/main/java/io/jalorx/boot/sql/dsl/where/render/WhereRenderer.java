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
package io.jalorx.boot.sql.dsl.where.render;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.jalorx.boot.sql.dsl.SqlCriterion;
import io.jalorx.boot.sql.dsl.render.RenderingStrategy;
import io.jalorx.boot.sql.dsl.render.TableAliasCalculator;
import io.jalorx.boot.sql.dsl.util.FragmentAndParameters;
import io.jalorx.boot.sql.dsl.util.FragmentCollector;
import io.jalorx.boot.sql.dsl.where.WhereModel;

public class WhereRenderer {
    private final WhereModel whereModel;
    private final CriterionRenderer criterionRenderer;

    private WhereRenderer(Builder builder) {
        whereModel = Objects.requireNonNull(builder.whereModel);

        criterionRenderer = new CriterionRenderer.Builder()
                .withSequence(builder.sequence)
                .withRenderingStrategy(builder.renderingStrategy)
                .withTableAliasCalculator(builder.tableAliasCalculator)
                .withParameterName(builder.parameterName)
                .build();
    }

    public Optional<WhereClauseProvider> render() {
        List<RenderedCriterion> renderedCriteria = whereModel.mapCriteria(this::render)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (renderedCriteria.isEmpty()) {
            return Optional.empty();
        }

        // The first is rendered without the initial connector because we don't want something like
        // where and(id = ?).  This can happen if the first condition doesn't render.
        FragmentAndParameters initialCriterion = renderedCriteria.get(0).fragmentAndParameters();

        FragmentCollector fc = renderedCriteria.stream()
                .skip(1)
                .map(RenderedCriterion::fragmentAndParametersWithConnector)
                .collect(FragmentCollector.collect(initialCriterion));

        WhereClauseProvider wcp = WhereClauseProvider.withWhereClause(calculateWhereClause(fc))
                .withParameters(fc.parameters())
                .build();
        return Optional.of(wcp);
    }

    private Optional<RenderedCriterion> render(SqlCriterion criterion) {
        return criterion.accept(criterionRenderer);
    }

    private String calculateWhereClause(FragmentCollector collector) {
        return collector.fragments()
                .collect(Collectors.joining(" ", "where ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public static Builder withWhereModel(WhereModel whereModel) {
        return new Builder().withWhereModel(whereModel);
    }

    public static class Builder {
        private WhereModel whereModel;
        private RenderingStrategy renderingStrategy;
        private TableAliasCalculator tableAliasCalculator;
        private AtomicInteger sequence;
        private String parameterName;

        public Builder withWhereModel(WhereModel whereModel) {
            this.whereModel = whereModel;
            return this;
        }

        public Builder withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }

        public Builder withTableAliasCalculator(TableAliasCalculator tableAliasCalculator) {
            this.tableAliasCalculator = tableAliasCalculator;
            return this;
        }

        public Builder withSequence(AtomicInteger sequence) {
            this.sequence = sequence;
            return this;
        }

        public Builder withParameterName(String parameterName) {
            this.parameterName = parameterName;
            return this;
        }

        public WhereRenderer build() {
            return new WhereRenderer(this);
        }
    }
}
