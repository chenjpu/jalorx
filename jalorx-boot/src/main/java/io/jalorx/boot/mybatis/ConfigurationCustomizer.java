package io.jalorx.boot.mybatis;

import org.apache.ibatis.session.Configuration;

public interface ConfigurationCustomizer {

	/**
	 * Customize the given a {@link Configuration} object.
	 * 
	 * @param configuration the configuration object to customize
	 */
	void customize(Configuration configuration);

}
