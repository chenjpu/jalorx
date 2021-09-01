package io.jalorx.boot.mybatis;

import static io.micronaut.core.util.ArrayUtils.isEmpty;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;

import io.jalorx.boot.datasource.RoutingDataSourceWrapper;
import io.jalorx.boot.mybatis.dialect.DialectUtil;
import io.jalorx.boot.mybatis.scripting.ExtDefaultLanguageDriver;
import io.jalorx.boot.mybatis.tx.SqlSessionTemplate;
import io.jalorx.boot.mybatis.tx.transaction.MicronautManagedTransactionFactory;
import io.jalorx.boot.mybatis.type.BooleanTypeHandler;
import io.micronaut.context.BeanLocator;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.transaction.jdbc.DelegatingDataSource;

@Factory
public class MybatisFactory {
	private static final Log LOGGER = LogFactory.getLog(MybatisFactory.class);

	private final TypeHandler<?>[]          typeHandlers;
	private final ConfigurationCustomizer[] customizers;

	public MybatisFactory(TypeHandler<?>[] typeHandlers, ConfigurationCustomizer[] customizers) {
		this.typeHandlers = typeHandlers;
		this.customizers  = customizers;
	}

	@Context
	SqlSessionFactory sessionFactory(BeanLocator beanLocator) {

		DataSource dataSource = DelegatingDataSource.unwrapDataSource(beanLocator
				.findBean(RoutingDataSourceWrapper.class)
				.map(w -> w.getDataSource())
				.orElseGet(() -> {
					return beanLocator.getBean(DataSource.class);
				}));
		// 设置资源加载包装cl，处理mybatis dtd文件转化
		ClassLoader classLoader = new ResourceClassLoaderWrapper(this.getClass()
				.getClassLoader());
		Resources.setDefaultClassLoader(classLoader);

		TransactionFactory transactionFactory = new MicronautManagedTransactionFactory();
		Environment        environment        = new Environment("developpment", transactionFactory, dataSource);

		Configuration configuration = createConfiguration(environment);

		configuration.getTypeHandlerRegistry()
				.register(BooleanTypeHandler.class);

		if (!isEmpty(this.typeHandlers)) {
			for (TypeHandler<?> typeHandler : this.typeHandlers) {
				configuration.getTypeHandlerRegistry()
						.register(typeHandler);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Registered type handler: '" + typeHandler + "'");
				}
			}
		}
		DatabaseIdProvider idProvider = DefautlDatabaseIdProvider.get();
		try {
			String databaseId = idProvider.getDatabaseId(dataSource);
			DialectUtil.registerDefault(databaseId);
			configuration.setDatabaseId(databaseId);
		} catch (SQLException e) {
			LOGGER.error("Failed getting a databaseId", e);
		}

		if (!isEmpty(this.customizers)) {
			for (ConfigurationCustomizer configurationCustomizer : this.customizers) {
				configurationCustomizer.customize(configuration);
			}
		}
		return new SqlSessionFactoryBuilder().build(configuration);
	}

	@Context
	SqlSessionTemplate createSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	private Configuration createConfiguration(Environment environment) {
		Configuration cf = new Configuration(environment);

		// new SettingsBuilder(cf).settingsElement(this.properties.getSettings());

		cf.setDefaultScriptingLanguage(ExtDefaultLanguageDriver.class);
		cf.setMapUnderscoreToCamelCase(true);
		return cf;
	}

	private final static class ResourceClassLoaderWrapper extends ClassLoader {

		static final String MYBATIS_MAPPER_DTD = "META-INF/mybatis-3-mapper.dtd";

		static final Map<String, String> RES_MAP = new HashMap<>(2);

		static {
			RES_MAP.put("org/apache/ibatis/builder/xml/mybatis-3-mapper.dtd", MYBATIS_MAPPER_DTD);
		}

		ResourceClassLoaderWrapper(ClassLoader cl) {
			super(cl);
		}

		public URL getResource(String name) {
			if (RES_MAP.containsKey(name)) {
				return super.getResource(RES_MAP.get(name));
			}
			return super.getResource(name);
		}

	};
}
