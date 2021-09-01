package io.jalorx.boot.security;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.Validate;

import jakarta.inject.Singleton;

/**
 * @author chenb
 * 
 *         sha256实现
 */
@Singleton
public class SHA256Encoder implements Encoder {

	@Override
	public String encode(@NotNull CharSequence raw) {
		Validate.notNull(raw, "encode raw is null");
		return DigestUtils.sha256Hex(raw.toString());
	}
}
