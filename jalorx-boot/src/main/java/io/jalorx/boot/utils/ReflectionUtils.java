package io.jalorx.boot.utils;

import java.util.Collection;

public class ReflectionUtils {

	/**
	 * 是否为集合类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isCollection(Class<?> clazz) {
		return Collection.class.isAssignableFrom(clazz) || clazz.isArray();
	}
}