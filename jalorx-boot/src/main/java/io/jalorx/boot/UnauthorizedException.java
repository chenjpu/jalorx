/**
 * 
 */
package io.jalorx.boot;

import io.jalorx.boot.errors.ErrCode;

/**
 * 未授权异常
 * 
 * @author chenb
 *
 */
public class UnauthorizedException extends BusinessAccessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6943291184765209480L;

	public UnauthorizedException(String permission) {
		super(ErrCode.A_UNAUTHORIZED, permission);
	}

}
