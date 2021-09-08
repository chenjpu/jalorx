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
package io.jalorx.boot.sql.dsl;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import io.jalorx.boot.sql.dsl.select.SelectModel;
import io.jalorx.boot.sql.dsl.util.Buildable;

public class ExistsPredicate {
    private final Buildable<SelectModel> selectModelBuilder;
    private final String operator;

    private ExistsPredicate(String operator, Buildable<SelectModel> selectModelBuilder) {
        this.selectModelBuilder = Objects.requireNonNull(selectModelBuilder);
        this.operator = Objects.requireNonNull(operator);
    }

    public String operator() {
        return operator;
    }

    public Buildable<SelectModel> selectModelBuilder() {
        return selectModelBuilder;
    }

    @NotNull
    public static ExistsPredicate exists(Buildable<SelectModel> selectModelBuilder) {
        return new ExistsPredicate("exists", selectModelBuilder); //$NON-NLS-1$
    }

    @NotNull
    public static ExistsPredicate notExists(Buildable<SelectModel> selectModelBuilder) {
        return new ExistsPredicate("not exists", selectModelBuilder); //$NON-NLS-1$
    }
}
