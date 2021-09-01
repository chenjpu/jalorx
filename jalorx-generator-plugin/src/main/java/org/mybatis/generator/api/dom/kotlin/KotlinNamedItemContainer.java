/**
 *    Copyright 2006-2019 the original author or authors.
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
package org.mybatis.generator.api.dom.kotlin;

import java.util.ArrayList;
import java.util.List;

public abstract class KotlinNamedItemContainer extends KotlinNamedItem {
    private List<KotlinNamedItem> namedItems = new ArrayList<>();

    public KotlinNamedItemContainer(NamedItemContainerBuilder<?> builder) {
        super(builder);
        namedItems.addAll(builder.namedItems);
    }

    public void addNamedItem(KotlinNamedItem namedItem) {
        namedItems.add(namedItem);
    }
    
    public List<KotlinNamedItem> getNamedItems() {
        return namedItems;
    }

    public abstract static class NamedItemContainerBuilder<T extends NamedItemContainerBuilder<T>>
            extends AbstractBuilder<T> {

        private List<KotlinNamedItem> namedItems = new ArrayList<>();

        protected NamedItemContainerBuilder(String name) {
            super(name);
        }

        public T withNamedItem(KotlinNamedItem namedItem) {
            namedItems.add(namedItem);
            return getThis();
        }

        protected abstract T getThis();
    }
}
