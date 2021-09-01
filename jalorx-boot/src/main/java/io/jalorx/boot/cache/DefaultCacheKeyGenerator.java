package io.jalorx.boot.cache;

import io.micronaut.cache.interceptor.CacheKeyGenerator;
import io.micronaut.cache.interceptor.ParametersKey;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.util.ArrayUtils;

@Introspected
public class DefaultCacheKeyGenerator implements CacheKeyGenerator {
	@Override
	public Object generateKey(AnnotationMetadata annotationMetadata, Object... params) {
		if (ArrayUtils.isEmpty(params)) {
			return ParametersKey.ZERO_ARG_KEY;
		}
		if (params.length == 1) {
			Object param = params[0];
			if (param != null && !param.getClass()
					.isArray()) {
				return param;
			} else {
				return new ParametersKey(params);
			}
		} else {
			return new ParametersKey(params);
		}
	}
}