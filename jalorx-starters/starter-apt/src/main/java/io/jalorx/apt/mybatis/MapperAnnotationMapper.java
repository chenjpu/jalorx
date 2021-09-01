package io.jalorx.apt.mybatis;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import io.jalorx.apt.AbstractAnnotationMapper;
import io.jalorx.boot.annotation.Mapper;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.annotation.AnnotationValueBuilder;
import io.micronaut.inject.visitor.VisitorContext;

/**
 * Maps {@code @Mapper} to {@code io.jalorx.annotation.Mapper}.
 *
 */
public class MapperAnnotationMapper extends AbstractAnnotationMapper {

    @Override
    public String getName() {
        return "org.apache.ibatis.annotations.Mapper";
    }

    @Override
    protected List<AnnotationValue<?>> mapInternal(AnnotationValue<Annotation> annotation, VisitorContext visitorContext) {
        final AnnotationValueBuilder<?> builder = AnnotationValue.builder(Mapper.class);
        return Collections.singletonList(builder.build());
    }
}