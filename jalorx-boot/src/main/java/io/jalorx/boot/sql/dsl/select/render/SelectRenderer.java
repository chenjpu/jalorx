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

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.jalorx.boot.sql.SelectProvider;
import io.jalorx.boot.sql.dsl.SortSpecification;
import io.jalorx.boot.sql.dsl.render.RenderingStrategy;
import io.jalorx.boot.sql.dsl.select.OrderByModel;
import io.jalorx.boot.sql.dsl.select.PagingModel;
import io.jalorx.boot.sql.dsl.select.QueryExpressionModel;
import io.jalorx.boot.sql.dsl.select.SelectModel;
import io.jalorx.boot.sql.dsl.util.CustomCollectors;
import io.jalorx.boot.sql.dsl.util.FragmentAndParameters;
import io.jalorx.boot.sql.dsl.util.FragmentCollector;

public class SelectRenderer {
    private final SelectModel selectModel;
    private final RenderingStrategy renderingStrategy;
    private final AtomicInteger sequence;

    private SelectRenderer(Builder builder) {
        selectModel = Objects.requireNonNull(builder.selectModel);
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
        sequence = builder.sequence().orElseGet(() -> new AtomicInteger(1));
    }

    public <T> SelectProvider<T> render(Class<T> clazz) {
        FragmentCollector fragmentCollector = selectModel
                .mapQueryExpressions(this::renderQueryExpression)
                .collect(FragmentCollector.collect());
        renderOrderBy(fragmentCollector);
        renderPagingModel(fragmentCollector);

        String selectStatement = fragmentCollector.fragments().collect(Collectors.joining(" ")); //$NON-NLS-1$

        return DefaultSelectStatementProvider.<T>withSelectStatement(selectStatement)
                .withParameters(fragmentCollector.parameters())
                .build(clazz);
    }

    private FragmentAndParameters renderQueryExpression(QueryExpressionModel queryExpressionModel) {
        return QueryExpressionRenderer.withQueryExpression(queryExpressionModel)
                .withRenderingStrategy(renderingStrategy)
                .withSequence(sequence)
                .build()
                .render();
    }

    private void renderOrderBy(FragmentCollector fragmentCollector) {
        selectModel.orderByModel().ifPresent(om -> renderOrderBy(fragmentCollector, om));
    }

    private void renderOrderBy(FragmentCollector fragmentCollector, OrderByModel orderByModel) {
        String phrase = orderByModel.mapColumns(this::calculateOrderByPhrase)
                .collect(CustomCollectors.joining(", ", "order by ", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        fragmentCollector.add(FragmentAndParameters.withFragment(phrase).build());
    }

    private String calculateOrderByPhrase(SortSpecification column) {
        String phrase = column.orderByName();
        if (column.isDescending()) {
            phrase = phrase + " DESC"; //$NON-NLS-1$
        }
        return phrase;
    }

    private void renderPagingModel(FragmentCollector fragmentCollector) {
        selectModel.pagingModel().flatMap(this::renderPagingModel)
            .ifPresent(fragmentCollector::add);
    }

    private Optional<FragmentAndParameters> renderPagingModel(PagingModel pagingModel) {
        return new PagingModelRenderer.Builder()
                .withPagingModel(pagingModel)
                .withRenderingStrategy(renderingStrategy)
                .withSequence(sequence)
                .build()
                .render();
    }

    public static Builder withSelectModel(SelectModel selectModel) {
        return new Builder().withSelectModel(selectModel);
    }

    public static class Builder {
        private SelectModel selectModel;
        private RenderingStrategy renderingStrategy;
        private AtomicInteger sequence;

        public Builder withSelectModel(SelectModel selectModel) {
            this.selectModel = selectModel;
            return this;
        }

        public Builder withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }

        public Builder withSequence(AtomicInteger sequence) {
            this.sequence = sequence;
            return this;
        }

        private Optional<AtomicInteger> sequence() {
            return Optional.ofNullable(sequence);
        }

        public SelectRenderer build() {
            return new SelectRenderer(this);
        }
    }
}
