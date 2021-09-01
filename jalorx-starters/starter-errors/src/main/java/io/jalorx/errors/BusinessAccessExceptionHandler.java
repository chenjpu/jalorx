package io.jalorx.errors;

import jakarta.inject.Singleton;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.errors.AbstractExceptionHandler;
import io.jalorx.boot.errors.ErrCode;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;


/**
 * 也无异常处理框架
 * @author chenb
 *
 */
@Produces
@Singleton
public class BusinessAccessExceptionHandler extends AbstractExceptionHandler<BusinessAccessException> {

  @Override
  protected ErrCode toErrCode(BusinessAccessException e) {
    return e.getErrCode();
  }

  @Override
  protected HttpStatus toStatus(BusinessAccessException e) {
    return HttpStatus.BAD_REQUEST;
  }

}

