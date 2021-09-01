package io.jalorx.boot.security;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpResponseFactory;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;

/**
 * @author lll
 *
 *         自定义http响应码
 */
public interface CustomHttpResponse<B> extends HttpResponse<B> {

	static <T> MutableHttpResponse<T> customRequest(HttpStatus httpStatus, T body) {
		return HttpResponseFactory.INSTANCE.status(httpStatus, body);
	}

}
