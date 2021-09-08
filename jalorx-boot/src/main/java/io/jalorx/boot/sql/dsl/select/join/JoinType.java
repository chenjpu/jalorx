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

import java.util.Optional;

public enum JoinType {
    INNER(),
    LEFT("left"), //$NON-NLS-1$
    RIGHT("right"), //$NON-NLS-1$
    FULL("full"); //$NON-NLS-1$

    private final String shortType;

    JoinType() {
        shortType = null;
    }

    JoinType(String shortType) {
        this.shortType = shortType;
    }

    public Optional<String> shortType() {
        return Optional.ofNullable(shortType);
    }
}
