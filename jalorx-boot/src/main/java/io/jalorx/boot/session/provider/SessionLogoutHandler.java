package io.jalorx.boot.session.provider;

import java.util.Optional;

import io.micronaut.core.convert.value.MutableConvertibleValues;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.filters.SecurityFilter;
import io.micronaut.security.handlers.LogoutHandler;
import io.micronaut.session.Session;
import io.micronaut.session.http.HttpSessionFilter;
import jakarta.inject.Singleton;

/**
 * 
 * @since 2.0
 */
@Singleton
public class SessionLogoutHandler implements LogoutHandler {

	@Override
	public MutableHttpResponse<?> logout(HttpRequest<?> request) {
		MutableConvertibleValues<Object> attrs    = request.getAttributes();
		Optional<Session>                existing = attrs.get(HttpSessionFilter.SESSION_ATTRIBUTE, Session.class);
		if (existing.isPresent()) {
			Session session = existing.get();
			session.remove(SecurityFilter.AUTHENTICATION);
		}
		return HttpResponse.ok();
	}
}
