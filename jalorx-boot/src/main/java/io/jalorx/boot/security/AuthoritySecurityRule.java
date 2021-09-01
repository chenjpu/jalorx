package io.jalorx.boot.security;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.Validate;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.AuthInfo;
import io.jalorx.boot.UnauthorizedException;
import io.jalorx.boot.UncertifiedException;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.utils.AuthInfoUtils;
import io.micrometer.core.lang.Nullable;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.inject.ExecutableMethod;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.rules.SecurityRuleResult;
import io.micronaut.security.rules.SensitiveEndpointRule;
import io.micronaut.web.router.MethodBasedRouteMatch;
import io.micronaut.web.router.RouteMatch;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * Security rule implementation
 *
 * @author chenjpu
 * @since 2.0
 */
@Singleton
public class AuthoritySecurityRule extends AbstractSecurityRule {

	private static final Logger LOG = LoggerFactory.getLogger(AuthoritySecurityRule.class);

	public static final Integer ORDER = SensitiveEndpointRule.ORDER + 1000;

	private final Map<ExecutableMethod<?, ?>, Optional<Set<String>>> loginMap     = new ConcurrentHashMap<>(32);
	private final Map<ExecutableMethod<?, ?>, Optional<String>>      operationMap = new ConcurrentHashMap<>(32);

	@Inject
	Environment environment;

	@Override
	public Publisher<SecurityRuleResult>  check(HttpRequest<?> request, @Nullable RouteMatch<?> routeMatch, @Nullable Authentication authentication) {

		if (routeMatch == null || !(routeMatch instanceof MethodBasedRouteMatch)) {
			return Publishers.just(SecurityRuleResult.ALLOWED);
		}

		MethodBasedRouteMatch<?, ?> methodRouteMatch = (MethodBasedRouteMatch<?, ?>) routeMatch;

		return Publishers.just(loginMap
				.computeIfAbsent(methodRouteMatch.getExecutableMethod(), r -> this.secured(routeMatch))
				.map(set -> {
					if (set.contains(SecurityRule.IS_ANONYMOUS)) {
						return SecurityRuleResult.ALLOWED;
					}
					if (set.contains(SecurityRule.IS_AUTHENTICATED)) {
						boolean login = checkLogin();
						LOG.debug("Check Operation Login[{}]", login);
						if (login) {
							return SecurityRuleResult.ALLOWED;
						} else {
							throw new UncertifiedException();
						}
					}
					return SecurityRuleResult.UNKNOWN;
				})
				.orElseGet(() -> operationMap
						.computeIfAbsent(methodRouteMatch.getExecutableMethod(), r -> this.operation(routeMatch))
						.map(permission -> {
							// 受控服务必须登录状态
							AuthInfo subject = AuthInfoUtils.getAuthInfo();
							// 超级用户
							if (subject.isRoot()) {
								return SecurityRuleResult.ALLOWED;
							}

							if (subject.isPermitted(permission)) {
								LOG.debug("User[{}] has right for permission [{}]", subject.getAccount(), permission);
								return SecurityRuleResult.ALLOWED;
							}
							LOG.debug("User[{}] has no right for permission [{}]", subject.getAccount(), permission);
							throw new UnauthorizedException(permission);

						})
						.orElseGet(() -> {
							LOG.warn("Lack of @Operation.Anonymous for route [{}]", routeMatch);
							return SecurityRuleResult.ALLOWED;
						})));
	}

	/**
	 * 获取方法上的操作权限
	 * 
	 * @param routeMatch
	 * @return
	 */
	private Optional<String> operation(RouteMatch<?> routeMatch) {
		return routeMatch.findAnnotation(Operation.class)
				.map(op -> {
					// .方法对应的资源定义
					String serviceCode = environment.getProperty("service.code", String.class, "00000");

					AnnotationValue<Resource> res = routeMatch.getAnnotation(Resource.class);
					Validate.notNull(res, routeMatch.getDeclaringType() + " need @Resource");

					String permission = String.format("%s.%d.%03d", serviceCode, res.getRequiredValue(Integer.class), op
							.getRequiredValue(Integer.class));

					return Optional.of(permission);
				})
				.orElse(Optional.empty());
	}

	/**
	 * 获取方法上的授权值，主要是登录和匿名访问
	 * 
	 * @param routeMatch
	 * @return
	 */
	private Optional<Set<String>> secured(RouteMatch<?> routeMatch) {
		return routeMatch.findAnnotation(Secured.class)
				.map(op -> {
					Set<String> values = CollectionUtils.setOf(op.getRequiredValue(String[].class));
					return Optional.of(values);
				})
				.orElse(Optional.empty());
	}

	@Override
	public int getOrder() {
		return ORDER;
	}
}
