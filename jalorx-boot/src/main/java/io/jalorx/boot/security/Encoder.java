package io.jalorx.boot.security;

import io.micronaut.context.annotation.DefaultImplementation;

/**
 * @author chenb
 *
 *         字符串编码接口
 */
@DefaultImplementation(SHA256Encoder.class)
public interface Encoder {

	/**
	 * @param raw
	 * 
	 *        对支持序列进行编码
	 * @return
	 */
	String encode(CharSequence raw);
}
