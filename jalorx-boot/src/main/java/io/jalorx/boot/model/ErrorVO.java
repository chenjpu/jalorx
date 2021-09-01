package io.jalorx.boot.model;

import io.jalorx.boot.errors.ErrCode;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.AbstractResource;

/**
 * 异常信息
 * 
 * @author chenb
 *
 */
@Produces(MediaType.APPLICATION_JSON)
public class ErrorVO extends AbstractResource<ErrorVO> {

	public static String ATTR_NAME = ErrorVO.class.getName();

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 消息
	 */
	private String message;

	private String path;

	private Object[] args;

	public ErrorVO(String code) {
		this.code = code;
	}

	public ErrorVO(ErrCode errCode) {
		this(errCode.getCode(), errCode.getMessage());
	}

	public ErrorVO(String code, String message) {
		this.code    = code;
		this.message = message;
	}

	public ErrorVO(String code, String message, Object... args) {
		this.code    = code;
		this.message = message;
		this.args    = args;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (code != null) {
			builder.append(' ')
					.append(code)
					.append(" - ");
		}
		if (path != null) {
			builder.append(' ')
					.append(path)
					.append(" - ");
		}
		builder.append(message);
		return builder.toString();
	}
}
