package io.jalorx.boot.model;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jalorx.boot.RowRule;
import io.jalorx.boot.sql.Command;
import io.jalorx.boot.utils.QueryUtils;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class SimpleRowRule implements RowRule, Serializable {

	private static final String IS_ALL = "__ALL";

	/**
	 * 
	 */
	private static final long serialVersionUID = 8337641130679241846L;

	private String code;//编码
	private String defaultField;//默认字段名
	private String type;//数据类型
	private String operator;//操作符
	private String values;//值

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDefaultField() {
		return defaultField;
	}

	public void setDefaultField(String defaultField) {
		this.defaultField = defaultField;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}


	@Override
	@JsonIgnore
	public Command toCommand(String field) {
		return QueryUtils
				.parseCommand(isNotEmpty(field) ? field : defaultField + "_" + type + "_" + operator, values);
	}

	@Override
	@JsonIgnore
	public boolean isALL() {
		return IS_ALL.equals(this.values);
	}
}
