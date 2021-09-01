package io.jalorx.boot.security.provider;

import org.reactivestreams.Publisher;

import io.jalorx.boot.Account;
import io.jalorx.boot.security.SecurityUserPermissionService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class DefaultAccountFetcher implements AccountFetcher {

	@Inject
	SecurityUserPermissionService service;

	@Override
	public Publisher<Account> findByAccount(String account) {
		Account user = service.findByUserAcount(account);
		if (user != null) {
			return Mono.just(user);
		}
		return Mono.empty();
	}
}
