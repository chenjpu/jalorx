package io.jalorx.boot.router;

import io.micronaut.web.router.naming.ConfigurableUriNamingStrategy;

/**
 * 服务前缀自定义路径策略
 * 
 * @author chenb
 *
 */
class ServicePrefixUriNamingStrategy extends ConfigurableUriNamingStrategy {

	/**
	 * Constructs a new uri naming strategy for the given property.
	 *
	 * @param contextPath the "micronaut.server.service-path" property value
	 */
	public ServicePrefixUriNamingStrategy(String servicePath) {
		super(servicePath);
	}

}
