package io.jalorx.boot.security.jackson;

import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.jalorx.boot.AuthInfo;
import io.jalorx.boot.RowRule;
import io.jalorx.boot.model.SimpleAuthInfo;
import io.jalorx.boot.model.SimpleRowRule;
import io.micronaut.core.annotation.TypeHint;
import jakarta.inject.Singleton;

@Singleton
@TypeHint(typeNames = {
		"com.fasterxml.jackson.databind.PropertyNamingStrategy$SnakeCaseStrategy"
})
@SuppressWarnings("serial")
public class SecurityJacksonModule extends SimpleModule {

	/**
	 * Default constructor.
	 */
	public SecurityJacksonModule() {
		super("jalorx.security");
		SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
		resolver.addMapping(AuthInfo.class, SimpleAuthInfo.class);
		resolver.addMapping(RowRule.class, SimpleRowRule.class);
		this._abstractTypes = resolver;
	}
}