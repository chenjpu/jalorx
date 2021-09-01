/**
 * 
 */
package io.jalorx.boot.mybatis.mapping;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

/**
 * @author chenb
 *
 */
public class ExtRawSqlSource extends RawSqlSource {

	public ExtRawSqlSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
		super(configuration, rootSqlNode, parameterType);
	}

	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		return BoundSqlUtil.initAdditional(super.getBoundSql(parameterObject));
	}
}
