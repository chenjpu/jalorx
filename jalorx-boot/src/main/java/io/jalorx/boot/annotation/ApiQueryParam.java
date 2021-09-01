package io.jalorx.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 扩展的动态查询参数
 * 
 * @author chenb
 *
 */
@Parameters({
		@Parameter(in = ParameterIn.QUERY, example = "Q_fieldName_S_ST=value", array = @ArraySchema(schema = @Schema(type = "string")), required = false, allowEmptyValue = true, description = "[{过滤参数:key=value}]"
				+
				"过滤参数key格式:F_T_OP/F_OP<br>" +
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
				"例:<pre>fieldName_T_LT=value</pre>")})
@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ApiQueryParam {

}
