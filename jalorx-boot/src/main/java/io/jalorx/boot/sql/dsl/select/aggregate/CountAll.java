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
package io.jalorx.boot.sql.dsl.select.aggregate;

import io.jalorx.boot.sql.dsl.render.TableAliasCalculator;

public class CountAll extends AbstractCount {

    public CountAll() {
        super();
    }

    private CountAll(String alias) {
        super(alias);
    }

    @Override
    public String renderWithTableAlias(TableAliasCalculator tableAliasCalculator) {
        return "count(*)"; //$NON-NLS-1$
    }

    @Override
    public CountAll as(String alias) {
        return new CountAll(alias);
    }
}