package io.jalorx.boot.errors;

import java.util.List;

import io.jalorx.boot.model.ErrorVO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.hateoas.Resource;
import io.micronaut.http.server.exceptions.ExceptionHandler;

/**
 * 异常处理抽象父类
 * 
 * @author chenb
 *
 * @param <E> 处理的异常类型
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractExceptionHandler<E extends Throwable> extends BaseErrorHandler
		implements
		ExceptionHandler<E, HttpResponse<ErrorVO>> {

	@Override
	public final HttpResponse<ErrorVO> handle(HttpRequest request, E e) {
		return error(request, toStatus(e), e, this::toErrCode);
	}

	/**
	 * 返回内存错误
	 * 
	 * @param e
	 * @return
	 */
	protected abstract ErrCode toErrCode(E e);

	/**
	 * 返回http状态值
	 * 
	 * @param e
	 * @return
	 */
	protected abstract HttpStatus toStatus(E e);

	/**
	 * @param e
	 * @return
	 */
	protected List<Resource> embedded(E e) {
		//是否可以响应堆栈？？？
		return null;
	}

}
