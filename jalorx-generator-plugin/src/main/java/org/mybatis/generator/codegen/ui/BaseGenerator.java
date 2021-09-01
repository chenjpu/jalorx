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

public abstract class BaseGenerator extends AbstractJavaGenerator {



  public BaseGenerator(String project) {
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

    String type = getType();


    FullyQualifiedJavaType clazzType =
        valueOf(String.format("%s.ui.%s.%sResource", basePackage, domain.toLowerCase(), type));

    TopLevelClass topLevelClass = new TopLevelClass(clazzType);
    topLevelClass.setVisibility(JavaVisibility.PUBLIC);
    commentGenerator.addJavaFileComment(topLevelClass);


    topLevelClass.addImportedType("io.micronaut.validation.Validated");
    topLevelClass.addImportedType("jakarta.inject.Inject");
    topLevelClass.addImportedType("io.micronaut.http.annotation.Controller");
    topLevelClass.addImportedType("io.jalorx.boot.annotation.Operation");
    topLevelClass.addImportedType("io.jalorx.boot.annotation.Resource");
    topLevelClass.addImportedType("io.swagger.v3.oas.annotations.tags.Tag");


    topLevelClass.addAnnotation("@Validated");
    topLevelClass.addAnnotation(String.format("@Tag(name = \"%s\")", domain));
    topLevelClass.addAnnotation(String.format("@Controller(\"/%s\")", domain.toLowerCase()));
    topLevelClass.addAnnotation(
        String.format("@Resource(code = %s, desc = \"%s Resource\")", code, domain));
    topLevelClass.addAnnotation("@Operation." + type);

    String base = String.format("io.jalorx.boot.ui.Base%sResource<%s>", type,
        introspectedTable.getBaseRecordType());

    FullyQualifiedJavaType fqjt = valueOf(base);
    topLevelClass.setSuperClass(fqjt);
    topLevelClass.addImportedType(fqjt);



    FullyQualifiedJavaType service =
        valueOf(String.format("%s.service.%sService", basePackage, domain));

    topLevelClass.addImportedType(service);

    Field field = new Field("service", service);
    field.setVisibility(JavaVisibility.PRIVATE);
    field.addAnnotation("@Inject");
    topLevelClass.addField(field);

    Method method = new Method("getService");

    method.setReturnType(service);
    method.setVisibility(JavaVisibility.PROTECTED);
    method.addAnnotation("@Override");
    method.addBodyLine("return this.service;");
    topLevelClass.addMethod(method);

    return Arrays.asList(topLevelClass);
  }

  protected abstract String getType();
}
