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
package org.mybatis.generator.codegen.ui;

import static org.mybatis.generator.api.dom.java.FullyQualifiedJavaType.valueOf;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.Arrays;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

public class AuthenticatedGenerator extends AbstractJavaGenerator {

  public AuthenticatedGenerator(String project) {
    super(project);
  }

  @Override
  public List<CompilationUnit> getCompilationUnits() {
    progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
        introspectedTable.getFullyQualifiedTable().toString()));
    CommentGenerator commentGenerator = context.getCommentGenerator();


    String basePackage = introspectedTable.getBasePackage();
    String code = introspectedTable.getCode();
    String domain = getDomainObjectName();


    FullyQualifiedJavaType type = valueOf(
        String.format("%s.ui.%s.%s", basePackage, domain.toLowerCase(), "AuthenticatedResource"));

    TopLevelClass clazz = new TopLevelClass(type);
    clazz.setVisibility(JavaVisibility.PUBLIC);
    commentGenerator.addJavaFileComment(clazz);


    clazz.addImportedType("io.micronaut.validation.Validated");
    clazz.addImportedType("jakarta.inject.Inject");
    clazz.addImportedType("io.micronaut.http.annotation.Controller");
    clazz.addImportedType("io.swagger.v3.oas.annotations.tags.Tag");
    clazz.addImportedType("io.jalorx.boot.annotation.Menu");
    clazz.addImportedType("io.jalorx.boot.annotation.Operation");
    clazz.addImportedType("io.jalorx.boot.annotation.Resource");



    clazz.addAnnotation("@Validated");
    clazz.addAnnotation(String.format("@Tag(name = \"%s\")", domain));
    clazz.addAnnotation(String.format("@Controller(\"/%s\")", domain.toLowerCase()));
    clazz.addAnnotation(
        String.format("@Resource(code = %s, desc = \"%s Resource\")", code, domain));
    clazz.addAnnotation("@Menu");
    clazz.addAnnotation("@Operation.Login");
    FullyQualifiedJavaType service =
        valueOf(String.format("%s.service.%sService", basePackage, domain));

    clazz.addImportedType(service);

    Field field = new Field("service", service);
    field.setVisibility(JavaVisibility.PRIVATE);
    field.addAnnotation("@Inject");
    clazz.addField(field);

    Method method = new Method("getService");

    method.setReturnType(service);
    method.setVisibility(JavaVisibility.PROTECTED);
    // method.addAnnotation("@Override");
    method.addBodyLine("return this.service;");
    clazz.addMethod(method);

    return Arrays.asList(clazz);
  }
}
