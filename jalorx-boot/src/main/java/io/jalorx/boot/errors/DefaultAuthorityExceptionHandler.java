package io.jalorx.boot.errors;

import io.jalorx.boot.UnauthorizedException;
import io.jalorx.boot.UncertifiedException;
import io.jalorx.boot.model.ErrorVO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;

@Controller
@SuppressWarnings("rawtypes")
public class DefaultAuthorityExceptionHandler extends BaseErrorHandler {

	@Error(global = true, exception = UnauthorizedException.class)
	public HttpResponse<ErrorVO> forbiddenException(HttpRequest request, UnauthorizedException e) {
		return error(request, HttpStatus.FORBIDDEN, e.getErrCode(), null);

	}

	@Error(global = true, exception = UncertifiedException.class)
	public HttpResponse<ErrorVO> unLoginException(HttpRequest request, UncertifiedException e) {
		return error(request, HttpStatus.UNAUTHORIZED, e.getErrCode(), null);
	}
}
