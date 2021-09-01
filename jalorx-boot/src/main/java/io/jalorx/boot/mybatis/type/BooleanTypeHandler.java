package io.jalorx.boot.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes(JdbcType.CHAR)
@MappedTypes({Boolean.class,boolean.class})
public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

	private static final String YES = "1";
	private static final String NO  = "0";

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType)
			throws SQLException {
		boolean b = parameter.booleanValue();
		ps.setString(i, b ? YES : NO);
	}

	@Override
	public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return convertStringToBooelan(rs.getString(columnName));
	}

	@Override
	public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return convertStringToBooelan(rs.getString(columnIndex));
	}

	@Override
	public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return convertStringToBooelan(cs.getString(columnIndex));
	}

	private Boolean convertStringToBooelan(String strValue) throws SQLException {
		return YES.equals(strValue);
	}

}
