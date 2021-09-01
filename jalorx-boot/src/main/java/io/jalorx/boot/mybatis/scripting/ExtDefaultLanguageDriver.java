
package io.jalorx.boot.mybatis.scripting;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.Alias;

/**
 * 扩展默认的xml解析
 * 
 * @author chenb
 *
 */
@Alias("extXml")
public class ExtDefaultLanguageDriver extends XMLLanguageDriver {
	// private Map<String, NodeHandler> nodeHandlers = new HashMap<String, NodeHandler>();

	public SqlSource createSqlSource(Configuration configuration, XNode script,
			Class<?> parameterType) {
		// nodeHandlers.put("query", new QueryHandler());
		XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
		return builder.parseScriptNode();
	}
}
