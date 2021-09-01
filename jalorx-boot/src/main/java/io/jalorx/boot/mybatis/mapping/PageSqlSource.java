package io.jalorx.boot.mybatis.mapping;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

import io.jalorx.boot.mybatis.dialect.DialectUtil;

/**
 * @author chenb
 */
public class PageSqlSource implements SqlSource {

	protected final Configuration configuration;
	protected final SqlNode       rootSqlNode;
	protected final boolean       isDynamic;

	public PageSqlSource(Configuration configuration, SqlNode rootSqlNode, boolean isDynamic) {
		this.configuration      = configuration;
		this.rootSqlNode        = rootSqlNode;
		this.isDynamic          = isDynamic;
	}

	protected String getSql(String sql) {

		StringBuilder builder = new StringBuilder(sql.length() + 150);
		builder.append(DialectUtil.getLimitBefore())
				.append(' ');
		builder.append(sql)
				.append(' ');
		builder.append(DialectUtil.getLimitAfter());
		return builder.toString();
	}

	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		DynamicContext context = new DynamicContext(configuration, isDynamic ? parameterObject : null);
		BoundSqlUtil.initAdditional(context);
		rootSqlNode.apply(context);
		SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
		Class<?>         parameterType   = parameterObject == null ? Object.class : parameterObject.getClass();
		String           sql             = context.getSql();
		// if (dataPermissionAble) {
		// sql = BoundSqlUtil.mergeSql(configuration, sql);
		// }
		SqlSource sqlSource = sqlSourceParser
				.parse(getSql(sql), parameterType, isDynamic ? context.getBindings() : new HashMap<String, Object>());
		BoundSql  boundSql  = sqlSource.getBoundSql(parameterObject);
		if (isDynamic) {
			for (Map.Entry<String, Object> entry : context.getBindings()
					.entrySet()) {
				boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
			}
		}
		return BoundSqlUtil.initAdditional(boundSql);
	}

}
