package io.jalorx.boot.security;

import org.reactivestreams.Publisher;

import io.jalorx.boot.annotation.AuthToken;
import io.micrometer.core.lang.Nullable;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRuleResult;
import io.micronaut.web.router.MethodBasedRouteMatch;
import io.micronaut.web.router.RouteMatch;

// @Singleton
public class AuthtokenAnnotationRule extends AbstractSecurityRule {

	/**
	 * 过滤器，对登录成功的Request加入了写入操作日志的功能.
	 */
	@Override
	public Publisher<SecurityRuleResult> check(HttpRequest<?> request, @Nullable RouteMatch<?> routeMatch, @Nullable Authentication authentication) {

		if (routeMatch instanceof MethodBasedRouteMatch) {
			MethodBasedRouteMatch<?, ?> methodRoute = ((MethodBasedRouteMatch<?, ?>) routeMatch);
			if (methodRoute.hasAnnotation(AuthToken.class)) {
				if (!checkLogin()) {
					return Publishers.just(SecurityRuleResult.REJECTED);
				}

				//AnnotationValue<AuthToken> res = methodRoute.getAnnotation(AuthToken.class);
				//String token = res.get("token", String.class, "x-mos-token");

				// FIXME:基于valut校验token是否有效
				return Publishers.just(SecurityRuleResult.REJECTED);

			}
		}
		return Publishers.just(SecurityRuleResult.UNKNOWN);
	}

}
