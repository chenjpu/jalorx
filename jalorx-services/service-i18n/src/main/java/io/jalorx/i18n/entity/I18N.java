package io.jalorx.i18n.entity;


import io.jalorx.boot.annotation.Lookup;
import io.jalorx.boot.model.LongIdVO;
import io.micronaut.core.annotation.Introspected;

/**
 * 国际化实体类，用于存储语言-键-值.
 * 
 * @author Bruce
 */
@Introspected
public class I18N extends LongIdVO {

  /**
   * 生成UID.
   */
  private static final long serialVersionUID = 2488000171838908420L;

  /**
   *
   * @generated Mon May 09 11:24:00 CST 2016
   */
  private String keyCode;

  /**
   *
   * @generated Mon May 09 11:24:00 CST 2016
   */
  @Lookup(type = "LANGUAGE")
  private String languageCode;

  /**
   *
   * @generated Mon May 09 11:24:00 CST 2016
   */
  private String valueDesc;

  /**
   *
   *
   * @generated Mon May 09 11:24:00 CST 2016
   */
  public String getKeyCode() {
    return keyCode;
  }

  /**
   *
   * @generated Mon May 09 11:24:00 CST 2016
   */
  public void setKeyCode(String keyCode) {
    this.keyCode = keyCode;
  }

  /**
   *
   * @generated Mon May 09 11:24:00 CST 2016
   */
  public String getLanguageCode() {
    return languageCode;
  }

  /**
   *
   * @generated Mon May 09 11:24:00 CST 2016
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  /**
   *
   * @generated Mon May 09 11:24:00 CST 2016
   */
  public String getValueDesc() {
    return valueDesc;
  }

  /**
   *
   * @generated Mon May 09 11:24:00 CST 2016
   */
  public void setValueDesc(String valueDesc) {
    this.valueDesc = valueDesc;
  }
}

