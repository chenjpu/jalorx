package io.jalorx.i18n.entity;

import io.jalorx.boot.Pair;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class I18NDTO {

	private String keyCode;
	private String valueDesc;

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getValueDesc() {
		return valueDesc;
	}

	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}
	
	public Pair valueOf() {
		return new Pair(keyCode,valueDesc);
		
	}

}
