/**
 * 
 */
package io.jalorx.boot.mybatis.dialect;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.mybatis.Dialect;

/**
 * @author chenb
 *
 */
public class DialectUtil {

	private static final Logger LOG = LoggerFactory.getLogger(DialectUtil.class);

	private static volatile Dialect DEFAUTL;

	static {
		Dialect.register(new GBaseDialect());
		Dialect.register(new MySQLDialect());
		Dialect.register(new OracleDialect());
		Dialect.register(new PostgreSQLDialect());
		Dialect.register(new SQLServerDialect());
	}

	private static final ThreadLocal<Dialect> dialectHolder = new ThreadLocal<>();

	public static void registerDefault(String databaseId) {
		DEFAUTL = Dialect.of(databaseId);
	}

	public static void bind(String databaseId) {
		set(Dialect.of(databaseId));
	}

	public static void release() {
		if (LOG.isDebugEnabled()) {
			Dialect dialect = dialectHolder.get();
			if (dialect != null) {
				LOG.debug("Releasing datasource dialect `{}`", dialect.databaseId());
			}
		}
		dialectHolder.set(null);
	}

	static Dialect get() {
		return ObjectUtils.defaultIfNull(dialectHolder.get(), DEFAUTL);
	}

	static void set(Dialect dialect) {
		LOG.debug("Binding datasource dialect `{}`", dialect.databaseId());
		dialectHolder.set(dialect);
	}

	public static String getLimitBefore() {
		return get().limitBefore();
	}

	public static String getLimitAfter() {
		return get().limitAfter();
	}

	public static String geLimitBetween() {
		return get().limitBetween();
	}

}
