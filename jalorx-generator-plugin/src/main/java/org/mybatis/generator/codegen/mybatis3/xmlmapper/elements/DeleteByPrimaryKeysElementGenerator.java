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

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class DeleteByPrimaryKeysElementGenerator extends AbstractXmlElementGenerator {

  public DeleteByPrimaryKeysElementGenerator() {
    super();
  }

  @Override
  public void addElements(XmlElement parentElement) {
    XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

    answer.addAttribute(
        new Attribute("id", introspectedTable.getDeleteByPrimaryKeyStatementId() + "s")); //$NON-NLS-1$

    context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();
    sb.append("delete from "); //$NON-NLS-1$
    sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
    sb.append(" where");
    answer.addElement(new TextElement(sb.toString()));
    
    
    List<IntrospectedColumn> keyColumns = introspectedTable.getPrimaryKeyColumns();

    if (keyColumns.size() == 1) {
      for (IntrospectedColumn introspectedColumn : keyColumns) {
        sb.setLength(0);
        sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
        sb.append(" in "); //$NON-NLS-1$
        answer.addElement(new TextElement(sb.toString()));
        XmlElement foreach = new XmlElement("foreach");
        foreach.addAttribute(new Attribute("collection", "ids"));
        foreach.addAttribute(new Attribute("item", "id"));
        foreach.addAttribute(new Attribute("open", "("));
        foreach.addAttribute(new Attribute("separator", ","));
        foreach.addAttribute(new Attribute("close", ")"));
        foreach.addElement(new TextElement("#{id}"));
        answer.addElement(foreach);
      }
    } else {
      boolean and = false;

      XmlElement foreach = new XmlElement("foreach");

      foreach.addAttribute(new Attribute("collection", "ids"));
      foreach.addAttribute(new Attribute("item", "id"));
      foreach.addAttribute(new Attribute("open", "("));
      foreach.addAttribute(new Attribute("separator", ") or ("));
      foreach.addAttribute(new Attribute("close", ")"));
      for (IntrospectedColumn introspectedColumn : keyColumns) {
        sb.setLength(0);
        if (and) {
          sb.append(" and "); //$NON-NLS-1$
        } else {
          and = true;
        }
        sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
        sb.append(" = "); //$NON-NLS-1$
        sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "id."));
        foreach.addElement(new TextElement(sb.toString()));
      }
      answer.addElement(foreach);
    }

    if (context.getPlugins().sqlMapDeleteByPrimaryKeyElementGenerated(answer, introspectedTable)) {
      parentElement.addElement(answer);
    }
  }
}
