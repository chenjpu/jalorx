package io.jalorx.boot.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 过滤的查询条件 Q=[{过滤参数:key=value}]*
 * <p>
 * 过滤的查询参数名称格式必须为: field_T_OP/field_OP <br>
 * 其中field查询的字段名称， T代表该参数的类型(不出现则表示字符串类型S),OP代表操作. <br>
 * field 按驼峰命名，firstName 会编译成 first_name <br>
 * 
 * T位置值有<br>
 * 
 * <pre>
 *        Z boolean
 *        B byte
 *        I int
 *        L long
 *        F float
 *        J double
 *        S String *
 *        D Date * "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
 *        T Time * "HH:mm:ss"
 * </pre>
 * 
 * <br>
 * OP位置值有<br>
 * 
 * <pre>
 *         LT &lt;
 *         LE &lt;=
 *         GT &gt;
 *         GE &gt;=
 *         EQ =
 *         NE !=
 *         IN in
 *         NI not in
 *         LK like %aa%
 *         ST like aa%
 *         ED like %aa
 *         NL null
 *         NN not null
 * </pre>
 * 
 * <br>
 * 通用样例 <br>
 * 
 * <pre>
 *  fieldName_T_LT=value -&gt; field_name &lt;  value
 *  firstName_T_LE=value -&gt; field_name &lt;= value
 *  firstName_T_GT=value -&gt; field_name &gt;  value
 *  firstName_T_GE=value -&gt; field_name &gt;= value
 *  firstName_T_EQ=value -&gt; field_name =  value
 *  firstName_T_NE=value -&gt; field_name != value
 *  firstName_T_IN=v1,v2 -&gt; field_name in (v1,v2)
 *  firstName_T_NI=v1,v2 -&gt; field_name not in (v1,v2)
 *  firstName_LK=value   -&gt; field_name like '%value%'
 *  firstName_ST=value   -&gt; field_name like 'value%'
 *  firstName_ED=value   -&gt; field_name like '%value'
 *  firstName_NL         -&gt; field_name is null
 *  firstName_NN         -&gt; field_name is not null
 * </pre>
 * 
 * @author chenb
 */
public class QueryFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7766192859661114584L;

	private List<Command> commands = new ArrayList<>(10);

	/**
	 * 非Q打头的查询参数
	 */
	private Map<String, Object> params = new HashMap<>();

	public List<Command> getCommands() {
		return commands;
	}

	public int length() {
		return commands.size();
	}

	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	public void addCommand(Command e) {
		if (e != null) {
			commands.add(e);
		}

	}

	public void addParams(String key, Object value) {
		params.put(key, value);
	}

	public boolean isEmpty() {
		return commands.isEmpty();
	}

	public String toString() {
		return Objects.toString(commands.toArray()) + params.toString();
	}

	public int hashCode() {
		return commands.hashCode() >> 16 + params.hashCode();
	}

	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof QueryFilter) {
			QueryFilter anotherQueryFilter = (QueryFilter) anObject;
			return commands.equals(anotherQueryFilter.commands)
					&& params.equals(anotherQueryFilter.params);
		}
		return false;
	}

}
