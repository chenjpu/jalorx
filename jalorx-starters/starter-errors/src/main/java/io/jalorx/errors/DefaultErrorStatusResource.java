package io.jalorx.errors;

import io.jalorx.boot.errors.BaseErrorHandler;
import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.model.ErrorVO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;

@Controller
@SuppressWarnings("rawtypes")
public class DefaultErrorStatusResource extends BaseErrorHandler {

  @Error(status = HttpStatus.METHOD_NOT_ALLOWED, global = true)
  public HttpResponse<ErrorVO> methodNotAllowed(HttpRequest request) {
    return error(request, HttpStatus.METHOD_NOT_ALLOWED, ErrCode.A_PARAMETER_FORMAT_MISMATCH);
  }

  @Error(status = HttpStatus.UNSUPPORTED_MEDIA_TYPE, global = true)
  public HttpResponse<ErrorVO> unsupportedMediaType(HttpRequest request) {
    return error(request, HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrCode.A_PARAMETER_FORMAT_MISMATCH);
  }

}
