package io.jalorx.json.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

import io.jalorx.boot.Pair;
import io.jalorx.boot.annotation.Lookup;
import io.jalorx.boot.json.MetaDataCollector;

@Singleton
public class LookupMetaDataCollector extends MetaDataCollector<Lookup, Pair> {

  @Override
  protected List<Pair> collector(Lookup a, Object vaule) {
    if (vaule == null) {
      return null;
    }
    List<String> codes = Arrays.asList(StringUtils.split(vaule.toString(), ","));
    List<Pair> ids = new ArrayList<>(codes.size());
    String type = a.type();
    codes.forEach(code -> {
      ids.add(new Pair(type, code));
    });
    return ids;
  }

}

