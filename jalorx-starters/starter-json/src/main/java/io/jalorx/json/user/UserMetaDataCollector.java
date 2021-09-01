package io.jalorx.json.user;

import java.util.Arrays;
import java.util.List;

import jakarta.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

import io.jalorx.boot.annotation.User;
import io.jalorx.boot.json.MetaDataCollector;

@Singleton
public class UserMetaDataCollector extends MetaDataCollector<User, String> {

  @Override
  protected List<String> collector(User a, Object vaule) {
    if (vaule == null) {
      return null;
    }
    return Arrays.asList(StringUtils.split(vaule.toString(), ","));
  }

}

