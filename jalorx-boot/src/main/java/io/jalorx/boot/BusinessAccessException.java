/*
 * $Id: BusinessAccessException.java,v 1.2 2010/01/18 02:58:06 chenbing Exp $
 * 
 * 文件名称：BusinessAccessException.java
 * 
 * 创建日期：2006-9-7
 * 
 * 版权所有：J.Bob
 */

package io.jalorx.boot;

import io.jalorx.boot.errors.ErrCode;

/**
 * 所有业务操作异常结构体系的根对象。它是一个抽象对象，用户可以根具需要实现具体的业务操作异常
 * 
 * 比如操作对象是null对象，等等。这个地方如果我们的服务层有业务逻辑错误，会抛出BusinessAccessException异常
 * 
 * 这个异常体系也是运行时异常，用户可以选择不用编码捕获这些异常
 *
 * @author chenjpu
 *
 */
public class BusinessAccessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7750201126168763208L;

	private final ErrCode  errCode;
	private final Object[] args;

	/**
	 * @param errorCode 错误编码
	 * @param ex 异常
	 * @param args 格式化参数
	 */
	public BusinessAccessException(ErrCode errCode, Throwable ex, Object... args) {
		super(errCode.getMessage(), ex);
		this.errCode = errCode;
		this.args    = args;
	}

	/**
	 * @param errorCode 错误编码
	 * @param args 格式化参数
	 */
	public BusinessAccessException(ErrCode errCode, Object... args) {
		super(errCode.getMessage());
		this.errCode = errCode;
		this.args    = args;
	}

	/**
	 * 
	 * @return 错误编码
	 */
	public ErrCode getErrCode() {
		return errCode;
	}

	/**
	 * 
	 * @return 格式化参数
	 */
	public Object[] getArguments() {
		return args;
	}
}
