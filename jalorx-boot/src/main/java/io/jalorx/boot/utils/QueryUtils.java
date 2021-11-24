/**
 * 
 */
package io.jalorx.boot.utils;

import static io.jalorx.boot.utils.StringUtils.convertList;
import static io.jalorx.boot.utils.StringUtils.convertObject;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.BusinessAccessException;
import io.jalorx.boot.Pair;
import io.jalorx.boot.Pair.Q;
import io.jalorx.boot.errors.ErrCode;
import io.jalorx.boot.sql.Command;
import io.jalorx.boot.sql.Op;
import io.jalorx.boot.sql.QueryDsl;
import io.micronaut.http.HttpParameters;

/**
 * @author chenb
 *
 */
public class QueryUtils {
	private static final Logger logger = LoggerFactory.getLogger(QueryUtils.class);

	public static Map<String, String> toSingleValueMap(Map<String, String> query) {
		Map<String, String> map = new HashMap<>(query.size());
		query.forEach((key, value) -> {
			map.put(key, value);
		});
		return map;
	}

	public static QueryDsl parseMultiQuery(HttpParameters params) throws BusinessAccessException {
		QueryDsl queryFilter = new QueryDsl();

		params.forEach((key, value) -> {
			if ("Q".equals(key)) {
				for (String qq : value) {
					Pair p = Q.valueOf(qq);
					queryFilter.addCommand(parseCommand(p.getKey(), p.getValue()));
				}
			} else {
				queryFilter.addParams(key, StringUtils.trim(value.get(0)));
			}
		});

		return queryFilter;
	}

	public static Command parseCommand(String key, String value) throws BusinessAccessException {
		String[] fieldInfo    = StringUtils.split(key, "_");
		Object   convertValue = null;
		if (fieldInfo.length == 3 || fieldInfo.length == 2) {
			String name = fieldInfo[0];
			String type = fieldInfo.length == 3 ? fieldInfo[1] : "S";
			Op     op   = Op.toOp(fieldInfo[fieldInfo.length - 1]);
			if (op.needValue()) {
				convertValue = op.isMultiple() ? convertList(type, value) : convertObject(type, value);
				return new Command(type,name, op, convertValue);
			} else {
				return new Command(type,name, op);
			}
		} else {
			logger.error("Query param name [{}] is not right format[field(_T)?_OP].", key);
			throw new BusinessAccessException(ErrCode.A_BAD_REQUEST, key);
		}
	}
}
