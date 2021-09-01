package io.jalorx.boot.mybatis.scripting.xmltags;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import io.jalorx.boot.AuthInfo;
import io.jalorx.boot.RowRule;
import io.jalorx.boot.sql.Command;
import io.jalorx.boot.sql.Op;
import io.jalorx.boot.sql.QueryFilter;
import io.jalorx.boot.utils.AuthInfoUtils;

public class QuerySqlNode implements SqlNode {
	private String  open;
	private String  close;
	private String  expression;
	private String  mame;
	private String  rule;
	private boolean tenant;

	public QuerySqlNode(String open, String close, String exp, String mame, String rule, boolean tenant) {
		this.open       = open;
		this.close      = close;
		this.expression = exp;
		this.mame       = mame;
		this.rule       = rule;
		this.tenant     = tenant;
	}

	@Override
	public boolean apply(DynamicContext context) {

		Map<String, Object> bindings = context.getBindings();

		Map<?, ?> parameterObject = (Map<?, ?>) bindings.get(DynamicContext.PARAMETER_OBJECT_KEY);

		QueryFilter filter = null;

		if (parameterObject != null && parameterObject.containsKey(expression)) {
			filter = (QueryFilter) parameterObject.get(expression);
		}

		List<Command> commands = new LinkedList<>();
		if (filter != null) {
			commands.addAll(filter.getCommands());
		}

		if (tenant) {
			String tenantId = AuthInfoUtils.getCurrentTenantId();
			if (tenantId != null) {
				commands.add(new Command("tenantId", Op.EQ, tenantId));
			}
		}

		if (StringUtils.isNotEmpty(rule)) {
			AuthInfo info = AuthInfoUtils.getAuthInfo();
			
			if (!info.isDataALL()) {
				String[] ids  = StringUtils.split(rule, ",");
				for (String ruleExp : ids) {
					String[] exps    = StringUtils.split(ruleExp, "#", 2);
					RowRule  rowRule = info.getRowRule(exps[0]);
					if (rowRule != null && !rowRule.isALL()) {
						commands.add(rowRule.toCommand(exps.length == 2 ? exps[1] : null));
					}
				}
			}
		}

		if (commands.isEmpty()) {
			return true;
		}

		applyOpen(context);
		boolean       first   = true;
		StringBuilder builder = new StringBuilder();
		for (Command command : commands) {
			int uniqueNumber = context.getUniqueNumber();
			context.bind("_fq_item_" + uniqueNumber, command);
			if (!first) {
				builder.append(" AND ");
			} else {
				first = false;
			}
			builder.append(command.toSql(mame, uniqueNumber));
		}
		context.appendSql(builder.toString());
		applyClose(context);
		return true;
	}

	private void applyOpen(DynamicContext context) {
		if (open != null) {
			context.appendSql(open);
		}
	}

	private void applyClose(DynamicContext context) {
		if (close != null) {
			context.appendSql(close);
		}
	}
}
