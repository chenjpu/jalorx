package io.jalorx.security.service.impl;

import java.util.HashMap;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.jalorx.security.entity.User;
import io.jalorx.security.service.UserService;
import io.jalorx.security.service.UserTransferService;

/**
 * @author chenb
 */
@Singleton
public class UserTransferServiceImpl implements UserTransferService {

  @Inject
  UserService userService;

  private static Map<String, User> userMap = new HashMap<String, User>();

  @Override
  public User getUserId(long userId) {
    String key = String.valueOf(userId);
    if (userMap.containsKey(key)) {
      return userMap.get(key);
    }
    User user = userService.get(userId);
    if (user == null) {
      return null;
    }
    User mapUser = new User();
    mapUser.setAcount(user.getAcount());
    mapUser.setLastname(user.getLastname());
    mapUser.setEmail(user.getEmail());
    userMap.put(key, mapUser);
    return mapUser;
  }

}
