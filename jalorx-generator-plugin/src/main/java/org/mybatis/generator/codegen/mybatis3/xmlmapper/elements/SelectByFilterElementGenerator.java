/**
 * Copyright 2006-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SelectByFilterElementGenerator extends AbstractXmlElementGenerator {

  public SelectByFilterElementGenerator() {
    super();
  }

  @Override
  public void addElements(XmlElement parentElement) {
    XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

    answer.addAttribute(new Attribute("id", "select")); //$NON-NLS-1$
    if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
      answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
          introspectedTable.getResultMapWithBLOBsId()));
    } else {
      answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
          introspectedTable.getBaseResultMapId()));
    }
    answer.addAttribute(new Attribute("pageable", "true")); //$NON-NLS-1$

    context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();
    sb.append("select "); //$NON-NLS-1$

    if (stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
      sb.append('\'');
      sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
      sb.append("' as QUERYID,"); //$NON-NLS-1$
    }
    answer.addElement(new TextElement(sb.toString()));
    answer.addElement(getBaseColumnListElement());
    if (introspectedTable.hasBLOBColumns()) {
      answer.addElement(new TextElement(",")); //$NON-NLS-1$
      answer.addElement(getBlobColumnListElement());
    }

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
