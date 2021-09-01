package io.jalorx.boot.security;

import io.micronaut.context.annotation.DefaultImplementation;

/**
 * @author chenb
 * 
 *         密码器
 *
 */
@DefaultImplementation(SHA256PasswordEncoder.class)
public interface PasswordEncoder extends Encoder {

	/**
	 * @param rawPassword
	 * @param encodedPassword
	 * 
	 *        密码比对
	 * @return
	 */
	boolean matches(String rawPassword, String encodedPassword);
}
