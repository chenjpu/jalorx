package io.jalorx.boot.security.provider;

import java.util.Optional;

import org.reactivestreams.Publisher;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import io.micronaut.security.filters.SecurityFilter;
import io.micronaut.security.token.TokenAuthenticationFetcher;
import io.micronaut.session.Session;
import io.micronaut.session.http.HttpSessionFilter;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class SessionAuthenticationFetcher implements AuthenticationFetcher {

	/**
	 * The order of the fetcher.
	 */
	public static final Integer ORDER = TokenAuthenticationFetcher.ORDER - 100;

	@Override
	public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
		Optional<Session> opt = request.getAttributes()
				.get(HttpSessionFilter.SESSION_ATTRIBUTE, Session.class);
		if (opt.isPresent()) {
			Session          session = opt.get();
			Optional<Object> optObj  = session.get(SecurityFilter.AUTHENTICATION);
			if (optObj.isPresent()) {
				Object obj = optObj.get();
				if (obj instanceof Authentication) {
					return Mono.just((Authentication) obj);
				}
			}
		}
		return Mono.empty();
	}

	@Override
	public int getOrder() {
		return ORDER;
	}
}
