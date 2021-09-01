package io.jalorx.errors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jakarta.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;

import io.jalorx.boot.errors.AbstractExceptionHandler;
import io.jalorx.boot.errors.ErrCode;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Resource;
import io.micronaut.validation.exceptions.ConstraintExceptionHandler;

/**
 * 参数校验错误
 * 
 * @author chenb
 *
 */
@Produces
@Singleton
@Replaces(bean = ConstraintExceptionHandler.class)
public class ValidationExceptionHandler
    extends AbstractExceptionHandler<ConstraintViolationException> {

  @Override
  protected List<Resource> embedded(ConstraintViolationException e) {
    Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

    if (CollectionUtils.isEmpty(constraintViolations)) {
      return null;
    }

    List<Resource> errors = new ArrayList<>();
    for (ConstraintViolation<?> violation : constraintViolations) {
      errors.add(new JsonError(buildMessage(violation)));
    }

    return errors;
  }

  @Override
  protected ErrCode toErrCode(ConstraintViolationException exception) {
    return ErrCode.A_BAD_REQUEST;
  }
  
  @Override
  protected HttpStatus toStatus(ConstraintViolationException e) {
    return HttpStatus.BAD_REQUEST;
  }

  /**
   * Builds a message based on the provided violation.
   *
   * @param violation The constraint violation
   * @return The violation message
   */
  protected String buildMessage(ConstraintViolation<?> violation) {
    Path propertyPath = violation.getPropertyPath();
    StringBuilder message = new StringBuilder();
    Iterator<Path.Node> i = propertyPath.iterator();
    while (i.hasNext()) {
      Path.Node node = i.next();
      if (node.getKind() == ElementKind.METHOD || node.getKind() == ElementKind.CONSTRUCTOR) {
        continue;
      }
      message.append(node.getName());
      if (i.hasNext()) {
        message.append('.');
      }
    }
    message.append(": ").append(violation.getMessage());
    return message.toString();
  }
}
