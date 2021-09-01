package io.jalorx.json;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.order.OrderUtil;
import io.micronaut.jackson.JacksonConfiguration;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
@Replaces(factory = io.micronaut.jackson.ObjectMapperFactory.class)
public class ObjectMapperFactory extends io.micronaut.jackson.ObjectMapperFactory {

  @PostConstruct 
  public void initialize() {
    OrderUtil.sort(jacksonModules);
    OrderUtil.sort(serializers);
    OrderUtil.sort(deserializers);
    OrderUtil.sort(beanSerializerModifiers);
    OrderUtil.sort(beanDeserializerModifiers);
    OrderUtil.sort(keyDeserializers);
  }
  
  @Singleton
  @Primary
  @Named("json")
  public ObjectMapper objectMapper(@Nullable JacksonConfiguration jacksonConfiguration,
                                   @Nullable JsonFactory jsonFactory) {
    return super.objectMapper(jacksonConfiguration, jsonFactory);
  }
}
