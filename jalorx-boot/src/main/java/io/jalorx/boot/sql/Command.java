/**
 * 
 */
package io.jalorx.boot.sql;

import java.io.Serializable;
import java.util.Objects;

import io.jalorx.boot.sql.dsl.SqlColumn;
import io.jalorx.boot.sql.dsl.select.QueryExpressionDSL;
import io.jalorx.boot.sql.dsl.select.SelectModel;

/**
 * @author chenb
 *
 */
public class Command implements Serializable {

	public static final Command DENY_ALL = new Command("denyAll", Op.DENY_ALL);

	/**
	 * 
	 */
	private static final long serialVersionUID = -105312395358927876L;
	final String              field;
	final Op                  operator;
	final Object              value;

	public Command(String field, Op operator) {
		this(field, operator, null);
	}

	public Command(String field, Op operator, Object value) {
		this.field    = field;
		this.operator = operator;
		this.value    = escape(value);
	}

	private Object escape(Object value) {
		switch (this.operator) {
		case LK:
		case ST:
		case ED:
			if (value != null) {
				return value.toString()
						.replaceAll("[%_!]", "!$0");
			}
			break;
		default:
			break;
		}
		return value;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	public Object getLike() {
		return "%" + value + "%";
	}

	public Object getStart() {
		return value + "%";
	}

	public Object getEnd() {
		return "%" + value;
	}
	
	public void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder,SqlColumn<Object> column) {
		operator.and(builder, this, column);
	}

	@Override
	public String toString() {
		return field + operator.toString() + Objects.toString(value);
	}

	@Override
	public int hashCode() {
		int hash = field.hashCode() << 16;
		hash += (operator.hashCode() << 8);
		hash += Objects.hashCode(value);
		return hash;
	}

	@Override
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof Command) {
			Command anotherCommand = (Command) anObject;
			return field.equals(anotherCommand.field) && operator.equals(anotherCommand.operator)
					&& Objects.equals(value, anotherCommand.value);
		}
		return false;
	}
}
