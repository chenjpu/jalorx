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
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SelectCountByFilterElementGenerator extends
        AbstractXmlElementGenerator {

    public SelectCountByFilterElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
      XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

      answer.addAttribute(new Attribute("id", "getCount")); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultType","int"));

      context.getCommentGenerator().addComment(answer);

      StringBuilder sb = new StringBuilder();
      sb.append("select count(*)"); //$NON-NLS-1$

      answer.addElement(new TextElement(sb.toString()));

      sb.setLength(0);
      sb.append("from "); //$NON-NLS-1$
      sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
      answer.addElement(new TextElement(sb.toString()));
      answer.addElement(getFilterElement());

      if (context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, introspectedTable)) {
        parentElement.addElement(answer);
      }
    }
}
