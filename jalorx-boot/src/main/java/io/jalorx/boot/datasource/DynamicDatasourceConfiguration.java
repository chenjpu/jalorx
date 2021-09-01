package io.jalorx.boot.datasource;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import com.zaxxer.hikari.HikariConfig;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.convert.format.MapFormat;
import io.micronaut.core.naming.conventions.StringConvention;
import io.micronaut.jdbc.BasicJdbcConfiguration;
import io.micronaut.jdbc.CalculatedSettings;

@EachProperty(value = DynamicDatasourceConfiguration.PREFIX, primary = "default")
public class DynamicDatasourceConfiguration extends HikariConfig implements BasicJdbcConfiguration {
	static final String        PREFIX = "dynamic-datasources";
	private CalculatedSettings calculatedSettings;
	private String             name;
	private boolean            master;

	/**
	 * Constructor.
	 * 
	 * @param name name that comes from properties
	 */
	public DynamicDatasourceConfiguration(@Parameter String name) {
		super();
		this.name               = name;
		this.calculatedSettings = new CalculatedSettings(this);
	}

	/**
	 * Hikari validates against the fields instead of using getters so the following
	 * is required to populate the calculated values into the fields.
	 */
	@PostConstruct
	void postConstruct() {
		if (getConfiguredUrl() == null) {
			setUrl(getUrl());
		}
		if (getConfiguredDriverClassName() == null) {
			setDriverClassName(getDriverClassName());
		}
		if (getConfiguredUsername() == null) {
			setUsername(getUsername());
		}
		if (getConfiguredPassword() == null) {
			setPassword(getPassword());
		}
		if (getConfiguredValidationQuery() == null) {
			setValidationQuery(getValidationQuery());
		}
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getConfiguredUrl() {
		return getJdbcUrl();
	}

	@Override
	public String getUrl() {
		return calculatedSettings.getUrl();
	}

	/**
	 * Setter.
	 *
	 * @param url url of connection
	 */
	public void setUrl(String url) {
		setJdbcUrl(url);
	}

	@Override
	public String getConfiguredDriverClassName() {
		return super.getDriverClassName();
	}

	@Override
	public String getDriverClassName() {
		return calculatedSettings.getDriverClassName();
	}

	@Override
	public String getConfiguredUsername() {
		return super.getUsername();
	}

	@Override
	public String getUsername() {
		return calculatedSettings.getUsername();
	}

	@Override
	public String getConfiguredPassword() {
		return super.getPassword();
	}

	@Override
	public String getPassword() {
		return calculatedSettings.getPassword();
	}

	@Override
	public String getConfiguredValidationQuery() {
		return getConnectionTestQuery();
	}

	@Override
	public String getValidationQuery() {
		return calculatedSettings.getValidationQuery();
	}

	/**
	 * Setter.
	 *
	 * @param validationQuery string of query
	 */
	public void setValidationQuery(String validationQuery) {
		setConnectionTestQuery(validationQuery);
	}

	/**
	 * Get Jndi name.
	 * 
	 * @return jndiName
	 */
	public String getJndiName() {
		return getDataSourceJNDI();
	}

	/**
	 * Setter.
	 * 
	 * @param jndiName jndi name
	 */
	public void setJndiName(String jndiName) {
		setDataSourceJNDI(jndiName);
	}

	/**
	 * Sets the data source properties.
	 * 
	 * @param dsProperties The datasource properties
	 */
	public void setDataSourceProperties(
			@MapFormat(transformation = MapFormat.MapTransformation.FLAT, keyFormat = StringConvention.RAW) Map<String, ?> dsProperties) {
		super.getDataSourceProperties().putAll(dsProperties);
	}

	@Override
	public void setDataSourceProperties(Properties dsProperties) {
		// otherwise properties will be added twice
	}
}
