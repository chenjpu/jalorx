/**
 * 
 */
package io.jalorx.boot.utils;

import java.io.Serializable;

import org.apache.commons.lang3.Validate;

import io.micronaut.data.model.Pageable;

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
	
	public static Pageable from(int page, int pageSize) {
		return Pageable.from(page > 0 ? page - 1 : 0, pageSize);
	}

	public static void notNull(Object object, Serializable id) {
		Validate.notNull(object, String
				.format("[Assertion failed] - id[%s] is required; it must not be null", String.valueOf(id)));
	}
}
