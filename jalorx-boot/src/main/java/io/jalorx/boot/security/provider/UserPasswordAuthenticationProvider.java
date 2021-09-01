package io.jalorx.boot.security.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.Validate;
import org.reactivestreams.Publisher;

import io.jalorx.boot.Account;
import io.jalorx.boot.RowRule;
import io.jalorx.boot.model.RuntimeRole;
import io.jalorx.boot.model.SimpleAuthInfo;
import io.jalorx.boot.security.PasswordEncoder;
import io.micrometer.core.lang.Nullable;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

@Requires(beans = {AccountFetcher.class,AuthFetcher.class})
@Singleton
public class UserPasswordAuthenticationProvider implements AuthenticationProvider {

	private final AccountFetcher  accountFetcher;
	private final AuthFetcher     authFetcher;
	private final PasswordEncoder passwordEncoder;

	public UserPasswordAuthenticationProvider(AccountFetcher accountFetcher, AuthFetcher authFetcher,
			PasswordEncoder passwordEncoder) {
		this.accountFetcher  = accountFetcher;
		this.authFetcher     = authFetcher;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
			AuthenticationRequest<?, ?> authenticationRequest) {
		return Flux.from(fetchAccount(authenticationRequest))
				.switchMap(account -> {
					//账号过期
					if (account.isCredentialsExpired()) {
						return Flux.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_EXPIRED));
					}
					//账号锁定
					if (account.isLocked()) {
						return Flux.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_LOCKED));
					}
					//账号不可用
					if (!account.isEnabled()) {
						return Flux.just(new AuthenticationFailed(AuthenticationFailureReason.CUSTOM));
					}

					Object secret = authenticationRequest.getSecret();
					Validate.notNull(secret, "authenticate secret is null");
					if (passwordEncoder.matches(secret.toString(), account.getPassword())) {
						return createSuccessfulAuthenticationResponse(authenticationRequest, account);
					} else {
						return Flux.just(new AuthenticationFailed(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH));
					}
				})
				.switchIfEmpty(Flux.just(new AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND)));
	}

	@SuppressWarnings("rawtypes")
	protected Publisher<Account> fetchAccount(AuthenticationRequest authenticationRequest) {
		final String account = authenticationRequest.getIdentity()
				.toString();
		return accountFetcher.findByAccount(account);
	}

	/**
	 * Create a successful
	 * {@link io.micronaut.security.authentication.AuthenticationResponse}.
	 *
	 * @param authenticationRequest The authentication request data
	 * @param user A representation of the state of a user after authentication.
	 * @return An AuthenticationResponse object which encapsulates a successful
	 *         authentication result.
	 */
	@SuppressWarnings("rawtypes")
	protected Publisher<AuthenticationResponse> createSuccessfulAuthenticationResponse(
			AuthenticationRequest authenticationRequest, Account account) {
		Collection<RuntimeRole> runtimeRoles = authFetcher.findRuntimeRolesByUserID(account.getId());

		Collection<String> roles = new ArrayList<>(runtimeRoles.size());
		
		RuntimeRole runtimeRole = null;

		for (RuntimeRole r : runtimeRoles) {
			runtimeRole = r;
			roles.add(r.toString());
		}

		SimpleAuthInfo authInfo = new SimpleAuthInfo(roles, account);

		if (runtimeRole != null) {
			authInfo.switchRole(runtimeRole.toString());
			if (!authInfo.isRoot()) {
				Collection<String> permissions = authFetcher.findAuthoritiesByRoleID(runtimeRole.getRoleID());
				authInfo.setPermissions(permissions);
			}

			if (!authInfo.isDataALL()) {
				Collection<RowRule> rowRules = authFetcher.findDataRowRuleByRuleID(runtimeRole.getDataID());
				authInfo.setRowRules(rowRules);
			}
		}

		return Publishers.just(authInfo);
	}

}
