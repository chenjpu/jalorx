package io.jalorx.errors;

import jakarta.inject.Singleton;

import org.apache.ibatis.binding.BindingException;

import io.jalorx.boot.errors.AbstractExceptionHandler;
import io.jalorx.boot.errors.ErrCode;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;


/**
 * mybatis数据库参数绑定异常
 * @author chenb
 *
 */
@Produces
@Singleton
public class MybatisBindingExceptionHandler extends AbstractExceptionHandler<BindingException> {

  @Override
  protected ErrCode toErrCode(BindingException e) {
    return ErrCode.B_RESOURCE_ERROR;
  }

  @Override
  protected HttpStatus toStatus(BindingException e) {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

}

