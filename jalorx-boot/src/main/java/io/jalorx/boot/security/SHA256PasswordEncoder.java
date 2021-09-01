package io.jalorx.boot.security;

import org.apache.commons.lang3.StringUtils;

import jakarta.inject.Singleton;

/**
 * @author chenb
 * 
 *         sha256实现
 */
@Singleton
public class SHA256PasswordEncoder extends SHA256Encoder implements PasswordEncoder {

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {

		return StringUtils.equals(super.encode(rawPassword), encodedPassword);
	}

}
