package io.jalorx.boot.security.provider;

import org.reactivestreams.Publisher;

import io.jalorx.boot.Account;
import io.micronaut.context.annotation.DefaultImplementation;

@DefaultImplementation(DefaultAccountFetcher.class)
public interface AccountFetcher {

	/**
	 * Fetches a user based on the username.
	 *
	 * @param account e.g. admin
	 * @return The users information or an empty publisher if no user is present
	 */
	Publisher<Account> findByAccount(String account);
}
