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
package org.mybatis.generator.codegen.service;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.Arrays;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

public class ServiceGenerator extends AbstractJavaGenerator {

  public ServiceGenerator(String project) {
    super(project);
  }

  @Override
  public List<CompilationUnit> getCompilationUnits() {
    progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
        introspectedTable.getFullyQualifiedTable().toString()));
    CommentGenerator commentGenerator = context.getCommentGenerator();



    FullyQualifiedJavaType type = new FullyQualifiedJavaType(String.format("%s.service.%sService",
        introspectedTable.getBasePackage(), getDomainObjectName()));
    
    Interface interfaze = new Interface(type);
    interfaze.setVisibility(JavaVisibility.PUBLIC);
    commentGenerator.addJavaFileComment(interfaze);

    interfaze.addImportedType(
        new FullyQualifiedJavaType("javax.transaction.Transactional"));
    interfaze.addAnnotation("@Transactional");

    String rootInterface = "io.jalorx.boot.service.BaseService<"
        + introspectedTable.getBaseRecordType() + ">";


    FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
    interfaze.addSuperInterface(fqjt);
    interfaze.addImportedType(fqjt);

    return Arrays.asList(interfaze);
  }


}
