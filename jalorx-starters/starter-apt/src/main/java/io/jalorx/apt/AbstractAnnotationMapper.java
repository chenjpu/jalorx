package io.jalorx.apt;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.inject.annotation.NamedAnnotationMapper;
import io.micronaut.inject.visitor.VisitorContext;

/**
 * Abstract annotations Mapper.
 * 
 * @author chenb
 *
 */
public abstract class AbstractAnnotationMapper implements NamedAnnotationMapper {
  @Override
  public final List<AnnotationValue<?>> map(AnnotationValue<Annotation> annotation,
      VisitorContext visitorContext) {
    if (annotation == null || visitorContext == null) {
      return Collections.emptyList();
    }
    return mapInternal(annotation, visitorContext);
  }

  /**
   * Internal map implemenation that subclasses should implement.
   * 
   * @param annotation The annotation
   * @param visitorContext The visitor context
   * @return A list of annotations
   */
  protected abstract  List<AnnotationValue<?>> mapInternal(
       AnnotationValue<Annotation> annotation,  VisitorContext visitorContext);
}
