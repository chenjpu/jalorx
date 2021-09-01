package io.jalorx.json;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import io.jalorx.boot.json.MetaDataCollector;
import io.micronaut.core.order.Ordered;

@Singleton
public class BeanMetaDataModule extends SimpleModule implements Ordered {
  private static final long serialVersionUID = -3790522560638581805L;
  private static final Logger LOG = LoggerFactory.getLogger(BeanMetaDataModule.class);

  /**
   * Default constructor.
   */
  public BeanMetaDataModule(MetaDataCollector<? extends Annotation, ?>[] metaDataFilters) {
    setSerializerModifier(new MetaDataSerializerModifier(metaDataFilters));
  }


  private class MetaDataSerializerModifier extends BeanSerializerModifier {

    private final MetaDataCollector<? extends Annotation, ?>[] metaDataFilters;

    public MetaDataSerializerModifier(
        MetaDataCollector<? extends Annotation, ?>[] metaDataFilters) {
      this.metaDataFilters = metaDataFilters;
    }

    @Override
    public BeanSerializerBuilder updateBuilder(SerializationConfig config, BeanDescription beanDesc,
        BeanSerializerBuilder builder) {

      List<BeanPropertyWriter> beanProperties = builder.getProperties();

      List<BeanPropertyWriter> newProperties = new ArrayList<>(beanProperties.size());

      for (BeanPropertyWriter writer : beanProperties) {

        MetaDataBeanPropertyWriter newWriter = null;

        for (MetaDataCollector<? extends Annotation, ?> t : metaDataFilters) {
          Annotation a = writer.findAnnotation(t.annoType());
          if (a != null) {
            if (LOG.isDebugEnabled()) {
              LOG.debug("Field [{}] has meta type [@{}]", writer.getName(), t.annoName());
            }
            if (newWriter == null) {
              newWriter = new MetaDataBeanPropertyWriter(writer, t, a);
            } else {
              LOG.warn("Conflict meta type [@{}] for field [{}], exist [@{}]", t.annoName(),
                  writer.getName(), newWriter.annoName());
            }
          }
        }

        if (newWriter == null) {
          newProperties.add(writer);
        } else {
          newProperties.add(newWriter);
        }
      }
      builder.setProperties(newProperties);
      return builder;
    }
  }
}
