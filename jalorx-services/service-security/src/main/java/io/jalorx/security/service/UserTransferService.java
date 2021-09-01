package io.jalorx.security.service;

import javax.transaction.Transactional;

import io.jalorx.security.entity.User;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * @author chenb
 */
@Transactional
public interface UserTransferService {

  @ReadOnly
  public User getUserId(long userId);

}
