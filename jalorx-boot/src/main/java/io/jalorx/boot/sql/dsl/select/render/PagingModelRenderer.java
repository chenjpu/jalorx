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

import io.jalorx.boot.sql.dsl.render.RenderingStrategy;
import io.jalorx.boot.sql.dsl.select.PagingModel;
import io.jalorx.boot.sql.dsl.util.FragmentAndParameters;

public class PagingModelRenderer {
    private final RenderingStrategy renderingStrategy;
    private final PagingModel pagingModel;
    private final AtomicInteger sequence;

    private PagingModelRenderer(Builder builder) {
        renderingStrategy = Objects.requireNonNull(builder.renderingStrategy);
        pagingModel = Objects.requireNonNull(builder.pagingModel);
        sequence = Objects.requireNonNull(builder.sequence);
    }

    public Optional<FragmentAndParameters> render() {
        return pagingModel.limit().map(this::limitAndOffsetRender)
                .orElseGet(this::fetchFirstRender);
    }

    private Optional<FragmentAndParameters> limitAndOffsetRender(Long limit) {
        return new LimitAndOffsetPagingModelRenderer(renderingStrategy, limit,
                pagingModel, sequence).render();
    }

    private Optional<FragmentAndParameters> fetchFirstRender() {
        return new FetchFirstPagingModelRenderer(renderingStrategy, pagingModel, sequence).render();
    }

    public static class Builder {
        private RenderingStrategy renderingStrategy;
        private PagingModel pagingModel;
        private AtomicInteger sequence;

        public Builder withRenderingStrategy(RenderingStrategy renderingStrategy) {
            this.renderingStrategy = renderingStrategy;
            return this;
        }

        public Builder withPagingModel(PagingModel pagingModel) {
            this.pagingModel = pagingModel;
            return this;
        }

        public Builder withSequence(AtomicInteger sequence) {
            this.sequence = sequence;
            return this;
        }

        public PagingModelRenderer build() {
            return new PagingModelRenderer(this);
        }
    }
}
