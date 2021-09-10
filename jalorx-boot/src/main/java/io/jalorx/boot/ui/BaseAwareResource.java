package io.jalorx.boot.ui;

import java.util.Optional;

import io.jalorx.boot.sql.QueryDsl;
import io.jalorx.boot.utils.QueryUtils;
import io.micronaut.http.HttpParameters;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;

public interface BaseAwareResource {

	String QE = "[{过滤参数:key=value}]<br>" +
			"过滤参数key格式:F_T_OP<br>" +
			"其中F为字段名称,T为类型(默认为S),OP为操作<br>" +
			"F按驼峰命名<br> " +
			"T值:" +
			"<pre>" +
			"Z boolean   B byte<br>" +
			"I int       L long<br>" +
			"F float     J double<br>" +
			"S String    T Time  HH:mm:ss<br>" +
			"D Date  yyyy-MM-dd (HH:mm:ss)<br>" +
			"</pre>" +
			"OP值:" +
			"<pre>" +
			"LT &lt;          LE &lt;=<br>" +
			"GT &gt;          GE &gt;=<br>" +
			"EQ =          NE !=<br>" +
			"IN in         NI not in<br>" +
			"LK like %aa%  ST like aa%<br>" +
			"ED like %aa   NL null<br>" +
			"NN not null<br>" +
			"</pre>" +
			"例:fieldName_[ZBILFJSTD]_[LT|LE|GT|GE|EQ|NE|IN|NI|NI|LK|ST|ED|NL|NN]=value";

	/**
	 * 获取动态查询参数列表
	 * 
	 * @return
	 */
	default QueryDsl getQueryFilter() {
		Optional<HttpRequest<Object>> request = ServerRequestContext.currentRequest();
		return request.map(r -> getQueryFilter(r.getParameters()))
				.orElse(new QueryDsl());
	}

	default QueryDsl getQueryFilter(HttpParameters params) {
		return QueryUtils.parseMultiQuery(params);
	}

}
