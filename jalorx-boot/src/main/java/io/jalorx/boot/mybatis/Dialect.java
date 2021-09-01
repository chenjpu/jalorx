package io.jalorx.boot.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

/**
 * mybatis分页方言
 * 
 * @author chenb
 *
 */
public interface Dialect {

	static Map<String, Dialect> DIALECT = new HashMap<>(4);

	/**
	 * @ 登记注册
	 * 
	 * @param dialect
	 */
	static void register(Dialect dialect) {
		DIALECT.put(dialect.databaseId(), dialect);
	}

	/**
	 * @param databaseId
	 * @return
	 */
	static Dialect of(String databaseId) {
		return Validate.notNull(DIALECT.get(databaseId));
	}

	/**
	 * 
	 * @ 数据库标识
	 * 
	 * @return
	 */
	String databaseId();

	/**
	 * @return
	 */
	default String limitBefore() {
		return "";
	}

	/**
	 * @return
	 */
	default String limitBetween() {
		return "";
	}

	/**
	 * @return
	 */
	default String limitAfter() {
		return "";
	}

}
