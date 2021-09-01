package io.jalorx.boot.errors;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.model.ErrorVO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.hateoas.Link;
import io.micronaut.http.hateoas.Resource;

@SuppressWarnings("rawtypes")
public abstract class BaseErrorHandler {

	protected static final Logger LOG = LoggerFactory.getLogger(BaseErrorHandler.class);

	/**
	 * @param e 框架异常
	 * @return 错误描述对象，包括堆栈信息
	 */
	protected ErrCode exception(BusinessAccessException e) {
		return e.getErrCode();
	}

	/**
	 * @param e 待转化异常
	 * @return 错误描述对象，包括堆栈信息
	 */
	protected ErrCode exception(Throwable e) {
		BusinessAccessException bae = findBusinessAccessException(e);
		if (bae != null) {
			return exception(bae);
		}
		return ErrCode.B_EXEC_ERROR;
	}

	/**
	 * 在异常栈中检索BusinessAccessException异常
	 * 
	 * @param e 捕获异常
	 * @return 业务异常
	 */
	protected BusinessAccessException findBusinessAccessException(Throwable cause) {
		while (cause != null) {
			if (cause instanceof BusinessAccessException) {
				return (BusinessAccessException) cause;
			}
			cause = cause.getCause();
		}
		return null;
	}

	/**
	 * 构建错误对象
	 * 
	 * @param request 请求对象
	 * @param status 返回状态
	 * @param errCode 错误码
	 * @param embedded 内嵌错误
	 * @return
	 */

	protected HttpResponse<ErrorVO> error(HttpRequest request, HttpStatus status, ErrCode errCode,
			List<Resource> embedded) {
		LOG.error("Request Error {}, {}", status, errCode);
		// 错误响应忽略属性增强
		request.setAttribute(ErrorVO.ATTR_NAME, true);
		ErrorVO error = new ErrorVO(errCode);
		error.link(Link.SELF, Link.of(request.getUri()));
		if (embedded != null) {
			error.embedded("errors", embedded);
		}
		return HttpResponse.<ErrorVO>status(status)
				.body(error);
	}

	/**
	 * 构建错误对象
	 * 
	 * @param request 请求对象
	 * @param status 返回状态
	 * @param errCode 错误码
	 * @return
	 */

	protected HttpResponse<ErrorVO> error(HttpRequest request, HttpStatus status, ErrCode errCode) {
		return error(request, status, errCode, null);
	}

	/**
	 * 构建错误对象
	 * 
	 * @param request 请求对象
	 * @param status 返回状态
	 * @param bae 业务异常
	 * @param provider
	 * @param embedded 内嵌错误
	 * @return
	 */
	protected <E extends Throwable> HttpResponse<ErrorVO> error(HttpRequest request,
			HttpStatus status, E bae, Function<E, ErrCode> provider, List<Resource> embedded) {
		LOG.error(bae.getMessage(), bae);
		return error(request, status, provider.apply(bae), embedded);
	}

	/**
	 * 构建错误对象
	 * 
	 * @param request 请求对象
	 * @param status 返回状态
	 * @param bae 业务异常
	 * @param provider
	 * @return
	 */
	protected <E extends Throwable> HttpResponse<ErrorVO> error(HttpRequest request,
			HttpStatus status, E bae, Function<E, ErrCode> provider) {
		return error(request, status, bae, provider, null);
	}

	/**
	 * 构建错误对象
	 * 
	 * @param request 请求对象
	 * @param status 返回状态
	 * @param bae 异常
	 * @return
	 */
	protected HttpResponse<ErrorVO> error(HttpRequest request, HttpStatus status, Throwable bae) {
		return error(request, status, bae, this::exception, null);
	}

	/**
	 * 构建错误对象
	 * 
	 * @param request 请求对象
	 * @param status 返回状态
	 * @param bae 异常
	 * @param embedded 内嵌错误
	 * @return
	 */
	protected HttpResponse<ErrorVO> error(HttpRequest request, HttpStatus status, Throwable bae,
			List<Resource> embedded) {
		return error(request, status, bae, this::exception, embedded);
	}

	/**
	 * 构建错误对象
	 * 
	 * @param request 请求对象
	 * @param status 返回状态
	 * @param bae 异常
	 * @return
	 */
	protected HttpResponse<ErrorVO> error(HttpRequest request, HttpStatus status,
			BusinessAccessException bae) {
		return error(request, status, bae, this::exception, null);
	}

	/**
	 * 构建错误对象
	 * 
	 * @param request 请求对象
	 * @param status 返回状态
	 * @param bae 异常
	 * @param embedded 内嵌错误
	 * @return
	 */
	protected HttpResponse<ErrorVO> error(HttpRequest request, HttpStatus status,
			BusinessAccessException bae, List<Resource> embedded) {
		return error(request, status, bae, this::exception, embedded);
	}

}
