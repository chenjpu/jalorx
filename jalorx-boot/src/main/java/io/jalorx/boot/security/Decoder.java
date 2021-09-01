package io.jalorx.boot.security;

/**
 * @author chenb
 *
 *         字符串解码接口
 */
public interface Decoder {

	/**
	 * @param raw
	 * 
	 *        对支持序列进行解码
	 * @return
	 */
	String encode(CharSequence raw);
}
