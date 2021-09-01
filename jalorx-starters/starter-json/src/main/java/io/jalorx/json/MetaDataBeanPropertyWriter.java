package io.jalorx.json;

import java.io.IOException;
import java.lang.annotation.Annotation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;

import io.jalorx.boot.json.MetaDataCollector;

public class MetaDataBeanPropertyWriter extends BeanPropertyWriter {
  /**
   * 
   */
  private static final long serialVersionUID = 3933579347662726192L;

  private final MetaDataCollector<? extends Annotation, ?> filter;

  private final Annotation annotation;

  protected MetaDataBeanPropertyWriter(BeanPropertyWriter base,
      MetaDataCollector<? extends Annotation, ?> filter, Annotation annotation) {
    super(base);
    this.filter = filter;
    this.annotation = annotation;
  }

  public String annoName() {
    return this.filter.annoName();
  }

  protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type,
      SerializerProvider provider) throws JsonMappingException {
    JsonSerializer<Object> serializer = super._findAndAddDynamic(map, type, provider);

    return new WrapJsonSerializer(serializer);
  }

  public void assignSerializer(JsonSerializer<Object> ser) {
    super.assignSerializer(new WrapJsonSerializer(ser));
  }

  class WrapJsonSerializer extends JsonSerializer<Object> {

    private final JsonSerializer<Object> wraped;

    WrapJsonSerializer(JsonSerializer<Object> wraped) {
      this.wraped = wraped;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      filter.collector0(annotation, value);
      wraped.serialize(value, gen, serializers);
    }

    public Class<Object> handledType() {
      return wraped.handledType();
    }
  }
}

