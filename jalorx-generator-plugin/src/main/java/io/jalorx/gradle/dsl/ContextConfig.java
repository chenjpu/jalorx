package io.jalorx.gradle.dsl;

public class ContextConfig {
  
  private boolean forceBigDecimals = false;
  
  private String rootInterface = "io.jalorx.boot.dao.BaseDao";
  private String rootClass = "io.jalorx.boot.model.LongIdVO";
  public boolean isForceBigDecimals() {
    return forceBigDecimals;
  }
  public void setForceBigDecimals(boolean forceBigDecimals) {
    this.forceBigDecimals = forceBigDecimals;
  }
  public String getRootInterface() {
    return rootInterface;
  }
  public void setRootInterface(String rootInterface) {
    this.rootInterface = rootInterface;
  }
  public String getRootClass() {
    return rootClass;
  }
  public void setRootClass(String rootClass) {
    this.rootClass = rootClass;
  }
}
