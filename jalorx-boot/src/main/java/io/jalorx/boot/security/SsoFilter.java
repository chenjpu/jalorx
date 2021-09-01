package io.jalorx.boot.security;

import java.time.Instant;
import java.util.Optional;
import java.util.function.Supplier;

import org.reactivestreams.Publisher;

import io.jalorx.boot.AuthInfo;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.http.filter.ServerFilterPhase;
import io.micronaut.security.filters.SecurityFilter;
import io.micronaut.session.Session;
import io.micronaut.session.http.SessionForRequest;

@Filter("/**")
public class SsoFilter implements HttpServerFilter {

	private static final CharSequence AUTHENTICATION = SecurityFilter.AUTHENTICATION;

	public SsoFilter() {}

	@Override
	public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
		Optional<Session> session = SessionForRequest.find(request);

		Optional<AuthInfo> infoOption = session.map(s -> {
			s.setLastAccessedTime(Instant.now());
			return s.get(AUTHENTICATION, AuthInfo.class);
		})
				.orElse(Optional.empty());

		return infoOption.map(info -> {
			// request.getAttributes().put(AuthInfoContext.AUTHINFO_ATTRIBUTE, info);
			Supplier<Publisher<MutableHttpResponse<?>>> trace = () -> doReq(request, chain);
			return AuthInfoContext.with(info, trace);
		})
				.orElseGet(() -> doReq(request, chain));

	}

	protected Publisher<MutableHttpResponse<?>> doReq(HttpRequest<?> request,
			ServerFilterChain chain) {
		Publisher<MutableHttpResponse<?>> finalPublisher = chain.proceed(request);
		return finalPublisher;
	}

	@Override
	public int getOrder() {
		return ServerFilterPhase.SESSION.after() + 500;
	}
}
