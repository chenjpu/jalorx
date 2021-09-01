/**
 * 
 */
package io.jalorx.boot;

import io.jalorx.boot.errors.ErrCode;

/**
 * 未登录异常
 * 
 * @author chenb
 *
 */
public class UncertifiedException extends BusinessAccessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6943291184765209480L;

	public UncertifiedException() {
		super(ErrCode.A_LOGIN_EXPIRED);
	}
}
