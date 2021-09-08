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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.jalorx.boot.sql.SelectProvider;

public class DefaultSelectStatementProvider<T> implements SelectProvider<T> {
	private final Class<T> clazz;
    private final String selectStatement;
    private final Map<String, Object> parameters;

    private DefaultSelectStatementProvider(Class<T> clazz,Builder<T> builder) {
    	this.clazz = clazz;
        selectStatement = Objects.requireNonNull(builder.selectStatement);
        parameters = Collections.unmodifiableMap(Objects.requireNonNull(builder.parameters));
    }
    
    @Override
	public Class<T> getRootEntity() {
		return clazz;
	}

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String getSql() {
        return selectStatement;
    }

    public static <T> Builder<T> withSelectStatement(String selectStatement) {
        return new Builder<T>().withSelectStatement(selectStatement);
    }

    public static class Builder<T> {
        private String selectStatement;
        private final Map<String, Object> parameters = new HashMap<>();

        public Builder<T> withSelectStatement(String selectStatement) {
            this.selectStatement = selectStatement;
            return this;
        }

        public Builder<T> withParameters(Map<String, Object> parameters) {
            this.parameters.putAll(parameters);
            return this;
        }

        public SelectProvider<T> build(Class<T> clazz) {
            return new DefaultSelectStatementProvider<T>(clazz,this);
        }
    }
}
