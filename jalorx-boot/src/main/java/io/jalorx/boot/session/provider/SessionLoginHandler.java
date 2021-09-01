/*
 * Copyright 2017-2018 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.jalorx.boot.session.provider;

import java.util.Optional;

import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.security.CustomHttpResponse;
import io.jalorx.boot.vo.LoginResult;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.filters.SecurityFilter;
import io.micronaut.security.handlers.LoginHandler;
import io.micronaut.session.Session;
import io.micronaut.session.SessionStore;
import io.micronaut.session.http.SessionForRequest;
import jakarta.inject.Singleton;

/**
 * 
 * @since 2.0
 */
@Singleton
public class SessionLoginHandler implements LoginHandler {

	protected final SessionStore<Session> sessionStore;

	/**
	 * Constructor.
	 * 
	 * @param securitySessionConfiguration Security Session Configuration
	 * @param sessionStore The session store
	 */
	public SessionLoginHandler(SessionStore<Session> sessionStore) {
		this.sessionStore = sessionStore;
	}

	@Override
	public MutableHttpResponse<?> loginSuccess(Authentication authentication, HttpRequest<?> request) {
		Session session = findSession(request);
		session.put(SecurityFilter.AUTHENTICATION, authentication);
		LoginResult result = new LoginResult();
		result.setUser(authentication);
		result.setLoginStatus(1);

		result.setStatus(200);
		return HttpResponse.ok()
				.body(result);
	}

	@Override
	public MutableHttpResponse<ErrCode> loginFailed(AuthenticationResponse authenticationFailed, HttpRequest<?> request) {
		ErrCode responseErr = ErrCode.A_ERR;
		switch (creatReason(authenticationFailed.getMessage())) {
		case USER_NOT_FOUND:
			responseErr = ErrCode.A_NO_ACCOUNT;
			break;
		case CREDENTIALS_DO_NOT_MATCH:
			responseErr = ErrCode.A_PASSWORD_ERROR;
			break;
		case USER_DISABLED:
			responseErr = ErrCode.A_FROZEN_ACCOUNT;
			break;
		case ACCOUNT_EXPIRED:
			responseErr = ErrCode.A_ACCOUNT_EXPIRED;
			break;
		case ACCOUNT_LOCKED:
			responseErr = ErrCode.A_FROZEN_ACCOUNT;
			break;
		case PASSWORD_EXPIRED:
			responseErr = ErrCode.A_PASSWORD_EXPIRED;
			break;
		case CUSTOM:
			responseErr = ErrCode.A_LOGIN_ANOMALY;
			break;
		case UNKNOWN:
			responseErr = ErrCode.A_UNKNOWN;
			break;
		default:
			responseErr = ErrCode.A_UNKNOWN;
			break;
		}
		return CustomHttpResponse.customRequest(HttpStatus.BAD_REQUEST, responseErr);

	}

	private AuthenticationFailureReason creatReason(Optional<String> mesggae) {
		String        optional = mesggae.get();
		StringBuilder sb       = new StringBuilder(optional.toUpperCase());
		for (int i = 0; i < sb.length(); i++) {
			int end = i + 1;
			if (i == 0) {
				sb.replace(i, end, String.valueOf(Character.toUpperCase(sb.charAt(i))));
			}
			if (sb.charAt(i) == ' ') {
				sb.replace(i, end, "_");
				sb.replace(end, end + 1, String.valueOf(Character.toUpperCase(sb.charAt(i + 1))));
			}
		} ;
		return AuthenticationFailureReason.valueOf(sb.toString());
	}

	private Session findSession(HttpRequest<?> request) {
		return SessionForRequest.find(request)
				.orElseGet(() -> SessionForRequest.create(sessionStore, request));
	}

	@Override
	public MutableHttpResponse<?> loginRefresh(Authentication authentication, String refreshToken,
			HttpRequest<?> request) {
		throw new UnsupportedOperationException("Session based logins do not support refresh");
	}
}
