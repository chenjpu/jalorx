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
package org.mybatis.generator.codegen.mybatis3;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedKotlinFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.AbstractKotlinGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.service.ServiceGenerator;
import org.mybatis.generator.codegen.service.ServiceImplGenerator;
import org.mybatis.generator.codegen.ui.AnonymousGenerator;
import org.mybatis.generator.codegen.ui.AuthenticatedGenerator;
import org.mybatis.generator.codegen.ui.CreateGenerator;
import org.mybatis.generator.codegen.ui.DeleteGenerator;
import org.mybatis.generator.codegen.ui.ReadGenerator;
import org.mybatis.generator.codegen.ui.UpdateGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * Introspected table implementation for generating MyBatis3 artifacts.
 * 
 * @author Jeff Butler
 */
public class IntrospectedTableMyBatis3Impl extends IntrospectedTable {

  protected List<AbstractJavaGenerator> javaGenerators = new ArrayList<>();

  protected List<AbstractKotlinGenerator> kotlinGenerators = new ArrayList<>();

  protected AbstractXmlGenerator xmlMapperGenerator;

  public IntrospectedTableMyBatis3Impl() {
    super(TargetRuntime.MYBATIS3);
  }

  @Override
  public void calculateGenerators(List<String> warnings, ProgressCallback progressCallback) {
    calculateJavaModelGenerators(warnings, progressCallback);

    AbstractJavaClientGenerator javaClientGenerator =
        calculateClientGenerators(warnings, progressCallback);

    calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);

    calculateServiceGenerators(warnings, progressCallback);
    calculateResourceGenerators(warnings, progressCallback);
  }

  protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator,
      List<String> warnings, ProgressCallback progressCallback) {
    if (javaClientGenerator == null) {
      if (context.getSqlMapGeneratorConfiguration() != null) {
        xmlMapperGenerator = new XMLMapperGenerator();
      }
    } else {
      xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
    }

    initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
  }

  protected AbstractJavaClientGenerator calculateClientGenerators(List<String> warnings,
      ProgressCallback progressCallback) {
    if (!rules.generateJavaClient()) {
      return null;
    }

    AbstractJavaClientGenerator javaGenerator = createJavaClientGenerator();
    if (javaGenerator == null) {
      return null;
    }

    initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
    javaGenerators.add(javaGenerator);

    return javaGenerator;
  }

  protected void calculateServiceGenerators(List<String> warnings,
      ProgressCallback progressCallback) {

    AbstractJavaGenerator serviceGenerator = new ServiceGenerator(getClientProject());

    initializeAbstractGenerator(serviceGenerator, warnings, progressCallback);
    javaGenerators.add(serviceGenerator);

    AbstractJavaGenerator serviceImplGenerator = new ServiceImplGenerator(getClientProject());

    initializeAbstractGenerator(serviceImplGenerator, warnings, progressCallback);
    javaGenerators.add(serviceImplGenerator);

  }


  protected void calculateResourceGenerators(List<String> warnings, ProgressCallback progressCallback) {

    AbstractJavaGenerator anonymous = new AnonymousGenerator(getClientProject());
    initializeAbstractGenerator(anonymous, warnings, progressCallback);
    javaGenerators.add(anonymous);

    AbstractJavaGenerator authenticated = new AuthenticatedGenerator(getClientProject());
    initializeAbstractGenerator(authenticated, warnings, progressCallback);
    javaGenerators.add(authenticated);
    
    
    AbstractJavaGenerator create = new CreateGenerator(getClientProject());
    initializeAbstractGenerator(create, warnings, progressCallback);
    javaGenerators.add(create);
    
    AbstractJavaGenerator update = new UpdateGenerator(getClientProject());
    initializeAbstractGenerator(update, warnings, progressCallback);
    javaGenerators.add(update);
    
    
    AbstractJavaGenerator delete = new DeleteGenerator(getClientProject());
    initializeAbstractGenerator(delete, warnings, progressCallback);
    javaGenerators.add(delete);
    
    
    AbstractJavaGenerator read = new ReadGenerator(getClientProject());
    initializeAbstractGenerator(read, warnings, progressCallback);
    javaGenerators.add(read);
    

  }



  protected AbstractJavaClientGenerator createJavaClientGenerator() {
    if (context.getJavaClientGeneratorConfiguration() == null) {
      return null;
    }

    String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();

    AbstractJavaClientGenerator javaGenerator;
    if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
      javaGenerator = new JavaMapperGenerator(getClientProject());
    } else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
      javaGenerator = new JavaMapperGenerator(getClientProject());
    } else {
      javaGenerator = (AbstractJavaClientGenerator) ObjectFactory.createInternalObject(type);
    }

    return javaGenerator;
  }

  protected void calculateJavaModelGenerators(List<String> warnings,
      ProgressCallback progressCallback) {

    if (getRules().generatePrimaryKeyClass()) {
      AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator(getModelProject());
      initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
      javaGenerators.add(javaGenerator);
    }

    if (getRules().generateBaseRecordClass()) {
      AbstractJavaGenerator javaGenerator = new BaseRecordGenerator(getModelProject());
      initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
      javaGenerators.add(javaGenerator);
    }

    if (getRules().generateRecordWithBLOBsClass()) {
      AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator(getModelProject());
      initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
      javaGenerators.add(javaGenerator);
    }
  }

  protected void initializeAbstractGenerator(AbstractGenerator abstractGenerator,
      List<String> warnings, ProgressCallback progressCallback) {
    if (abstractGenerator == null) {
      return;
    }

    abstractGenerator.setContext(context);
    abstractGenerator.setIntrospectedTable(this);
    abstractGenerator.setProgressCallback(progressCallback);
    abstractGenerator.setWarnings(warnings);
  }

  @Override
  public List<GeneratedJavaFile> getGeneratedJavaFiles() {
    List<GeneratedJavaFile> answer = new ArrayList<>();

    for (AbstractJavaGenerator javaGenerator : javaGenerators) {
      List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
      for (CompilationUnit compilationUnit : compilationUnits) {
        GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit, javaGenerator.getProject(),
            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
            context.getJavaFormatter());
        answer.add(gjf);
      }
    }

    return answer;
  }

  @Override
  public List<GeneratedKotlinFile> getGeneratedKotlinFiles() {
    List<GeneratedKotlinFile> answer = new ArrayList<>();

    for (AbstractKotlinGenerator kotlinGenerator : kotlinGenerators) {
      List<KotlinFile> kotlinFiles = kotlinGenerator.getKotlinFiles();
      for (KotlinFile kotlinFile : kotlinFiles) {
        GeneratedKotlinFile gjf = new GeneratedKotlinFile(kotlinFile, kotlinGenerator.getProject(),
            context.getProperty(PropertyRegistry.CONTEXT_KOTLIN_FILE_ENCODING),
            context.getKotlinFormatter());
        answer.add(gjf);
      }
    }

    return answer;
  }

  protected String getClientProject() {
    return context.getJavaClientGeneratorConfiguration().getTargetProject();
  }

  protected String getModelProject() {
    return context.getJavaModelGeneratorConfiguration().getTargetProject();
  }

  protected String getExampleProject() {
    String project = context.getJavaModelGeneratorConfiguration()
        .getProperty(PropertyRegistry.MODEL_GENERATOR_EXAMPLE_PROJECT);

    if (StringUtility.stringHasValue(project)) {
      return project;
    } else {
      return getModelProject();
    }
  }

  @Override
  public List<GeneratedXmlFile> getGeneratedXmlFiles() {
    List<GeneratedXmlFile> answer = new ArrayList<>();

    if (xmlMapperGenerator != null) {
      Document document = xmlMapperGenerator.getDocument();
      GeneratedXmlFile gxf = new GeneratedXmlFile(document, getMyBatis3XmlMapperFileName(),
          getMyBatis3XmlMapperPackage(),
          context.getSqlMapGeneratorConfiguration().getTargetProject(), true,
          context.getXmlFormatter());
      if (context.getPlugins().sqlMapGenerated(gxf, this)) {
        answer.add(gxf);
      }
    }

    return answer;
  }

  @Override
  public int getGenerationSteps() {
    return javaGenerators.size() + (xmlMapperGenerator == null ? 0 : 1);
  }

  @Override
  public boolean requiresXMLGenerator() {
    AbstractJavaClientGenerator javaClientGenerator = createJavaClientGenerator();

    if (javaClientGenerator == null) {
      return false;
    } else {
      return javaClientGenerator.requiresXMLGenerator();
    }
  }
}
