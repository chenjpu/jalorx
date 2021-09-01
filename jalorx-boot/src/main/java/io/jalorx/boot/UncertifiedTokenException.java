/**
 * 
 */
package io.jalorx.boot;

/**
 * Token验证异常
 * 
 * @author chenjpu
 *
 */
public class UncertifiedTokenException extends RuntimeException {
	public UncertifiedTokenException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6943291184765209480L;

}
