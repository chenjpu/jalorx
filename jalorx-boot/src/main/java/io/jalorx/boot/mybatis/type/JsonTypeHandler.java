package io.jalorx.boot.mybatis.type;

/**
 * json格式的数据处理
 * 
 * @author chenb
 *
 * @param <T> 参数化类型
 * 
 *        public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
 * 
 * @Override public void setNonNullParameter(PreparedStatement ps, int i, T
 *           parameter, JdbcType jdbcType) throws SQLException { ps.setString(i,
 *           JSON.toJSONString(parameter)); }
 * 
 * @Override public T getNullableResult(ResultSet rs, String columnName) throws
 *           SQLException { return JSON.parseObject(rs.getString(columnName),
 *           getRawType()); }
 * 
 * @Override public T getNullableResult(ResultSet rs, int columnIndex) throws
 *           SQLException { return JSON.parseObject(rs.getString(columnIndex),
 *           getRawType()); }
 * 
 * @Override public T getNullableResult(CallableStatement cs, int columnIndex)
 *           throws SQLException { return
 *           JSON.parseObject(cs.getString(columnIndex), getRawType()); } }
 */
