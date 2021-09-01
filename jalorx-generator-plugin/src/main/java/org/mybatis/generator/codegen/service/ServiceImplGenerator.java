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
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import static org.mybatis.generator.api.dom.java.FullyQualifiedJavaType.valueOf;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

public class ServiceImplGenerator extends AbstractJavaGenerator {

  public ServiceImplGenerator(String project) {
    super(project);
  }

  @Override
  public List<CompilationUnit> getCompilationUnits() {
    progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
        introspectedTable.getFullyQualifiedTable().toString()));
    CommentGenerator commentGenerator = context.getCommentGenerator();


    String basePackage = introspectedTable.getBasePackage();
    String domain = getDomainObjectName();


    FullyQualifiedJavaType type =
        valueOf(String.format("%s.service.impl.%sServiceImpl", basePackage, domain));

    TopLevelClass topLevelClass = new TopLevelClass(type);
    topLevelClass.setVisibility(JavaVisibility.PUBLIC);
    commentGenerator.addJavaFileComment(topLevelClass);

    topLevelClass.addImportedType(valueOf("jakarta.inject.Inject"));
    topLevelClass.addImportedType(valueOf("jakarta.inject.Singleton"));

    topLevelClass.addAnnotation("@Singleton");

    String base = "io.jalorx.boot.service.impl.BaseServiceImpl<"
        + introspectedTable.getBaseRecordType() + ">";

    FullyQualifiedJavaType fqjt = valueOf(base);
    topLevelClass.setSuperClass(fqjt);
    topLevelClass.addImportedType(fqjt);


    FullyQualifiedJavaType intf =
        valueOf(String.format("%s.service.%sService", basePackage, domain));

    topLevelClass.addSuperInterface(intf);
    topLevelClass.addImportedType(intf);
    
    
    FullyQualifiedJavaType dao = valueOf(String.format("%s.dao.%sDao", basePackage, domain));
    
    topLevelClass.addImportedType(dao);

    Field field = new Field("dao", dao);
    field.setVisibility(JavaVisibility.PRIVATE);
    field.addAnnotation("@Inject");
    topLevelClass.addField(field);
    
    Method method = new Method("getDao");

    method.setReturnType(dao);
    method.setVisibility(JavaVisibility.PROTECTED);
    method.addAnnotation("@Override");
    method.addBodyLine("return this.dao;");
    topLevelClass.addMethod(method);

    return Arrays.asList(topLevelClass);
  }
}
