/**
 * 
 */
package io.jalorx.boot.utils;

import java.io.Serializable;

import org.apache.commons.lang3.Validate;

/**
 * @author chenb
 *
 */
public class DaoUtils {

	public static int realPage(int page, int pageSize, int length) {
		if ((page - 1) * pageSize > length) {
			page = (length + pageSize - 1) / pageSize;
		}
		return page;
	}

	public static void notNull(Object object, Serializable id) {
		Validate.notNull(object, String
				.format("[Assertion failed] - id[%s] is required; it must not be null", String.valueOf(id)));
	}
}
