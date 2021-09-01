package io.jalorx.errors;

import jakarta.inject.Singleton;

import org.apache.ibatis.exceptions.PersistenceException;

import io.jalorx.boot.errors.AbstractExceptionHandler;
import io.jalorx.boot.errors.ErrCode;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;


/**
 * mybatis数据库访问异常
 * @author chenb
 *
 */
@Produces
@Singleton
public class MybatisPersistenceExceptionHandler extends AbstractExceptionHandler<PersistenceException> {

  @Override
  protected ErrCode toErrCode(PersistenceException e) {
    return ErrCode.B_RESOURCE_ACCESS_ERROR;
  }

  @Override
  protected HttpStatus toStatus(PersistenceException e) {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

}

