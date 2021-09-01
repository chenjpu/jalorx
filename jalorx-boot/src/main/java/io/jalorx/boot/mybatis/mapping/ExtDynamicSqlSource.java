/**
 * 
 */
package io.jalorx.boot.mybatis.mapping;

import java.util.Map;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

/**
 * 替换为org.apache.ibatis.scripting.xmltags.DynamicSqlSource.<br/>
 * 为数据权限插入SQL解析算法
 * 
 * @author Bruce
 */
public class ExtDynamicSqlSource implements SqlSource {

	private Configuration configuration;
	private SqlNode       rootSqlNode;


	public ExtDynamicSqlSource(Configuration configuration, SqlNode rootSqlNode) {
		this.configuration      = configuration;
		this.rootSqlNode        = rootSqlNode;
	}

	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		DynamicContext context = new DynamicContext(configuration, parameterObject);
		BoundSqlUtil.initAdditional(context);
		rootSqlNode.apply(context);
		SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
		Class<?>         parameterType   = parameterObject == null ? Object.class : parameterObject.getClass();
		String           sql             = context.getSql();
		SqlSource sqlSource = sqlSourceParser.parse(sql, parameterType, context.getBindings());
		BoundSql  boundSql  = sqlSource.getBoundSql(parameterObject);
		for (Map.Entry<String, Object> entry : context.getBindings()
				.entrySet()) {
			boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
		}
		return BoundSqlUtil.initAdditional(boundSql);
	}

}
