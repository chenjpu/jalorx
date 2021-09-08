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
package io.jalorx.boot.sql.dsl.select.join;

import java.util.Objects;

import io.jalorx.boot.sql.dsl.BasicColumn;

public class JoinCriterion {

    private final String connector;
    private final BasicColumn leftColumn;
    private final JoinCondition joinCondition;

    private JoinCriterion(Builder builder) {
        connector = Objects.requireNonNull(builder.connector);
        leftColumn = Objects.requireNonNull(builder.joinColumn);
        joinCondition = Objects.requireNonNull(builder.joinCondition);
    }

    public String connector() {
        return connector;
    }

    public BasicColumn leftColumn() {
        return leftColumn;
    }

    public BasicColumn rightColumn() {
        return joinCondition.rightColumn();
    }

    public String operator() {
        return joinCondition.operator();
    }

    public static class Builder {
        private String connector;
        private BasicColumn joinColumn;
        private JoinCondition joinCondition;

        public Builder withConnector(String connector) {
            this.connector = connector;
            return this;
        }

        public Builder withJoinColumn(BasicColumn joinColumn) {
            this.joinColumn = joinColumn;
            return this;
        }

        public Builder withJoinCondition(JoinCondition joinCondition) {
            this.joinCondition = joinCondition;
            return this;
        }

        public JoinCriterion build() {
            return new JoinCriterion(this);
        }
    }
}
