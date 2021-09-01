package io.jalorx.gradle.dsl;

import org.gradle.api.Action;
import org.gradle.api.Project;

public class GeneratorExtension {
  final Project project;
  private boolean overwrite = false;
  // def configFile = "generatorConfig.xml"
  private boolean verbose = false;
  private String targetDir = ".";
  private String basePackage;
  private String tables;
  private String code;



  private final JdbcConnection jdbcConnection = new JdbcConnection();
  private final ContextConfig context = new ContextConfig();


  public GeneratorExtension(Project project) {
    this.project = project;
  }


  public boolean isOverwrite() {
    return overwrite;
  }

  public void setOverwrite(boolean overwrite) {
    this.overwrite = overwrite;
  }

  public boolean isVerbose() {
    return verbose;
  }

  public void setVerbose(boolean verbose) {
    this.verbose = verbose;
  }

  public String getTargetDir() {
    return targetDir;
  }

  public void setTargetDir(String targetDir) {
    this.targetDir = targetDir;
  }

  public String getTables() {
    return tables;
  }

  public void setTables(String tables) {
    this.tables = tables;
  }

  public String getBasePackage() {
    return basePackage;
  }

  public void setBasePackage(String basePackage) {
    this.basePackage = basePackage;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public JdbcConnection getJdbcConnection() {
    return jdbcConnection;
  }

  public ContextConfig getContext() {
    return context;
  }

  public void jdbcConnection(Action<? super JdbcConnection> action) {
    action.execute(jdbcConnection);
  }

  public void context(Action<? super ContextConfig> action) {
    action.execute(context);
  }



}
