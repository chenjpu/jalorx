package io.jalorx.boot.sql;

import java.util.List;

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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s < #{_fq_item_%d.value}", filterName, cmd.field, i);
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s > #{_fq_item_%d.value}", filterName, cmd.field, i);
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s = #{_fq_item_%d.value}", filterName, cmd.field, i);
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s != #{_fq_item_%d.value}", filterName, cmd.field, i);
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s <= #{_fq_item_%d.value}", filterName, cmd.field, i);
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s >= #{_fq_item_%d.value}", filterName, cmd.field, i);
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
		String toSql(String filterName, Command cmd, int i) {
			List<?>       list    = (List<?>) cmd.value;
			StringBuilder builder = new StringBuilder(30 * list.size());
			for (int j = 0, length = list.size(); j < length; j++) {
				builder.append(String.format("#{_fq_item_%d.value[%d]},", i, j));
			}
			builder.deleteCharAt(builder.length() - 1);
			return String.format("%s.%s IN (%s)", filterName, cmd.field, builder.toString());
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
		String toSql(String filterName, Command cmd, int i) {
			List<?>       list    = (List<?>) cmd.value;
			StringBuilder builder = new StringBuilder(30 * list.size());
			for (int j = 0, length = list.size(); j < length; j++) {
				builder.append(String.format("#{_fq_item_%d.value[%d]},", i, j));
			}
			builder.deleteCharAt(builder.length() - 1);
			return String.format("%s.%s NOT IN (%s)", filterName, cmd.field, builder.toString());
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s LIKE #{_fq_item_%d.like} ESCAPE '!'", filterName, cmd.field, i);
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s LIKE #{_fq_item_%d.start} ESCAPE '!'", filterName, cmd.field, i);
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s LIKE #{_fq_item_%d.end ESCAPE '!'", filterName, cmd.field, i);
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s is null", filterName, cmd.field);
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
		String toSql(String filterName, Command cmd, int i) {
			return "1 != 1";
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
		String toSql(String filterName, Command cmd, int i) {
			return String.format("%s.%s is not null", filterName, cmd.field);
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
	 * @param cmd 动作
	 * @param i 参数排序
	 * @return sql字符串
	 */
	abstract String toSql(String filterName, Command cmd, int i);

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
