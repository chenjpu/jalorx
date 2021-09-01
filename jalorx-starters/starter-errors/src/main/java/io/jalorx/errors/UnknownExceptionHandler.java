package io.jalorx.errors;

import jakarta.inject.Singleton;

import io.jalorx.boot.errors.AbstractExceptionHandler;
import io.jalorx.boot.errors.ErrCode;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;


/**
 * 也无异常处理框架
 * 
 * @author chenb
 *
 */
@Produces
@Singleton
public class UnknownExceptionHandler extends AbstractExceptionHandler<Exception> {

  @Override
  protected ErrCode toErrCode(Exception e) {
    return super.exception(e);
  }

  protected HttpStatus toStatus(Exception e) {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

}

