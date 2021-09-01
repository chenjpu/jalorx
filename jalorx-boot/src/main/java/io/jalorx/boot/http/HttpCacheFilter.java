package io.jalorx.boot.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.reactivestreams.Publisher;

import io.jalorx.boot.annotation.HttpCache;
import io.jalorx.boot.annotation.HttpNoCache;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpAttributes;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.http.filter.ServerFilterPhase;
import io.micronaut.web.router.RouteMatch;

/**
 * Responsible for handling http cache requests and responses.
 *
 */
@Filter("/**")
public class HttpCacheFilter implements HttpServerFilter {

	private static Map<String, String> SIMPLE_DIRECTIVE = new HashMap<>();

	static {
		SIMPLE_DIRECTIVE.put("mustRevalidate", "must-revalidate");
		SIMPLE_DIRECTIVE.put("noTransform", "no-transform");
		SIMPLE_DIRECTIVE.put("noStore", "no-store");
		SIMPLE_DIRECTIVE.put("proxyRevalidate", "proxy-revalidate");
	}

	@Override
	public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {

		RouteMatch<?> routeMatch = request.getAttribute(HttpAttributes.ROUTE_MATCH, RouteMatch.class)
				.orElse(null);

		//没有匹配路由或者不是GET请求，直接返回
		if (routeMatch == null || request.getMethod() != HttpMethod.GET) {
			return chain.proceed(request);
		}

		return Publishers.map(chain.proceed(request), mutableHttpResponse -> {
			handleResponse(routeMatch, request, mutableHttpResponse);
			return mutableHttpResponse;
		});
	}

	@Override
	public int getOrder() {
		return ServerFilterPhase.RENDERING.before();
	}

	/**
	 * Handles a http cache response.
	 * 
	 * @param routeMatch
	 * @param request
	 * @param response
	 */
	protected void handleResponse(RouteMatch<?> routeMatch, HttpRequest<?> request, MutableHttpResponse<?> response) {

		routeMatch.findDeclaredAnnotation(HttpCache.class)
				.ifPresent(ann -> {
					StringBuilder buffer = new StringBuilder();

					SIMPLE_DIRECTIVE.forEach((key, value) -> {
						ann.booleanValue(key)
								.ifPresent(t -> {
									if (t) {
										addDirective(value, buffer);
									}
								});
					});

					ann.intValue("maxAge")
							.ifPresent(value -> {
								addDirective("max-age", buffer).append("=")
										.append(value);
							});

					ann.intValue("sMaxAge")
							.ifPresent(value -> {
								addDirective("s-maxage", buffer).append("=")
										.append(value);
							});

					response.header(HttpHeaders.CACHE_CONTROL, buffer.toString());
				});

		routeMatch.findDeclaredAnnotation(HttpNoCache.class)
				.ifPresent(ann -> {

					StringBuilder buffer = new StringBuilder();

					String[] fields = ann.stringValues("fields");
					if (ArrayUtils.isEmpty(fields)) {
						addDirective("no-cache", buffer);
					} else {
						for (String field : fields) {
							addDirective("no-cache", buffer).append("=\"")
									.append(field)
									.append("\"");
						}
					}
					response.header(HttpHeaders.CACHE_CONTROL, buffer.toString());
				});
	}

	private static StringBuilder addDirective(String directive, StringBuilder buffer) {
		if (buffer.length() > 0)
			buffer.append(", ");
		buffer.append(directive);
		return buffer;
	}
}
