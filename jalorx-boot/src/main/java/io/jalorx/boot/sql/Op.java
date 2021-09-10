package io.jalorx.boot.sql;

import java.util.Collection;

import static io.jalorx.boot.sql.dsl.SqlBuilder.*;
import io.jalorx.boot.sql.dsl.SqlColumn;
import io.jalorx.boot.sql.dsl.select.QueryExpressionDSL;
import io.jalorx.boot.sql.dsl.select.SelectModel;

/**
 * 操作的封装
 * 
 * @author chenb
 *
 */
public enum Op {

	/**
	 * 小于
	 * 
	 * @author chenb
	 *
	 */
	LT {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
				SqlColumn<Object> column) {
			builder.and(column, isLessThan(cmd.value));
		}
	},
	/**
	 * 大于
	 * 
	 * @author chenb
	 *
	 */
	GT {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
				SqlColumn<Object> column) {
			builder.and(column, isGreaterThan(cmd.value));
		}
	},
	/**
	 * 等于
	 * 
	 * @author chenb
	 *
	 */
	EQ {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
				SqlColumn<Object> column) {
			builder.and(column, isEqualTo(cmd.value));
		}
	},
	/**
	 * 不等于
	 * 
	 * @author chenb
	 *
	 */
	NE {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
				SqlColumn<Object> column) {
			builder.and(column, isNotEqualTo(cmd.value));
		}
	},
	/**
	 * 小于等于
	 * 
	 * @author chenb
	 *
	 */
	LE {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
				SqlColumn<Object> column) {
			builder.and(column, isLessThanOrEqualTo(cmd.value));
		}
	},
	/**
	 * 大于等于
	 * 
	 * @author chenb
	 *
	 */
	GE {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
				SqlColumn<Object> column) {
			builder.and(column, isGreaterThanOrEqualTo(cmd.value));
		}
	},

	/**
	 * in
	 * 
	 * @author chenb
	 *
	 */
	IN {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
				SqlColumn<Object> column) {
			builder.and(column, isIn((Collection) cmd.value));
		}

		@Override
		public boolean isMultiple() {
			return true;
		}
	},
	/**
	 * not in
	 * 
	 * @author chenb
	 *
	 */
	NI {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
				SqlColumn<Object> column) {
			builder.and(column, isNotIn((Collection) cmd.value));
		}

		@Override
		public boolean isMultiple() {
			return true;
		}
	},
	/**
	 * like
	 * 
	 * @author chenb
	 *
	 */
	LK {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd, SqlColumn<Object> column) {
			builder.and(column, isLike(cmd.getLike()));
		}
	},
	/**
	 * like aa%
	 * 
	 * @author chenb
	 *
	 */
	ST {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd, SqlColumn<Object> column) {
			builder.and(column, isLike(cmd.getStart()));
		}
	},
	/**
	 * like %aa
	 * 
	 * @author chenb
	 *
	 */
	ED {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd, SqlColumn<Object> column) {
			builder.and(column, isLike(cmd.getEnd()));
		}
	},
	/**
	 * is null
	 * 
	 * @author chenb
	 *
	 */
	NL {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd, SqlColumn<Object> column) {
			builder.and(column, isNull());
		}

		@Override
		public boolean needValue() {
			return false;
		}
	},

	/**
	 * 内部使用
	 * 
	 * @author chenb
	 *
	 */
	DENY_ALL {
		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd, SqlColumn<Object> column) {
			builder.and(column, isNotEqualTo(column));
		}

		@Override
		public boolean needValue() {
			return false;
		}
	},
	/**
	 * is not null
	 * 
	 * @author chenb
	 *
	 */
	NN {

		@Override
		void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
				SqlColumn<Object> column) {
			builder.and(column, isNotNull());
		}

		@Override
		public boolean needValue() {
			return false;
		}
	};

	/**
	 * 操作转化成底层的数据库运算符
	 * 
	 * @param filterName 字段
	 * @param cmd        动作
	 * @return sql字符串
	 */
	abstract void and(QueryExpressionDSL<SelectModel>.QueryExpressionWhereBuilder builder, Command cmd,
			SqlColumn<Object> column);

	/**
	 * 比较的值是否是多值情况，IN
	 * 
	 * @return 多值
	 */
	public boolean isMultiple() {
		return false;
	}

	/**
	 * 是否需要关注左值 is null，not null 两个不关注
	 * 
	 * @return 是否需要处理值
	 */
	public boolean needValue() {
		return true;
	}

	/**
	 * 需要校验参数的有效性 FIXME：
	 * 
	 * @param operatorName 字符串名
	 * @return Op对象
	 */
	public static Op toOp(String operatorName) {
		return Op.valueOf(operatorName);
	}
}
