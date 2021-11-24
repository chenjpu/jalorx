package io.jalorx.boot.sql;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			compare(builder::lessThan, command, root);
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			compare(builder::greaterThan, command, root);
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.equal(root.get(command.field), command.value);
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.notEqual(root.get(command.field), command.value);
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			compare(builder::lessThanOrEqualTo, command, root);
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			compare(builder::greaterThanOrEqualTo, command, root);
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.in(root.get(command.field)).value(command.value);
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.not(builder.in(root.get(command.field)).value(command.value));
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.like(root.get(command.field), command.getLike());
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.like(root.get(command.field), command.getStart(), '!');
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.like(root.get(command.field), command.getEnd(), '!');
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.isNull(root.get(command.field));
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.notEqual(root.get(command.field), root.get(command.field));
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
		<T> void and(CriteriaBuilder builder, Command command, Root<T> root) {
			builder.isNotNull(root.get(command.field));
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
	abstract <T> void and(CriteriaBuilder builder, Command command, Root<T> root);

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

	static <Y extends Comparable<? super Y>, T> Predicate compare(Contrast c, Command command, Root<T> root) {
		switch (command.type) {
		case "S":
			 return c.test(root.get(command.field), (String) (command.value));
		case "Z":
			return c.test(root.get(command.field), (Boolean) (command.value));
		case "B":
			return c.test(root.get(command.field), (Byte) (command.value));
		case "I":
			return c.test(root.get(command.field), (Integer) (command.value));
		case "L":
			return c.test(root.get(command.field), (Long) (command.value));
		case "F":
			return c.test(root.get(command.field), (Float) (command.value));
		case "J":
			return c.test(root.get(command.field), (Double) (command.value));
		case "M":
			return c.test(root.get(command.field), (BigDecimal) (command.value));
		case "D":
		case "T":
			return c.test(root.get(command.field), (Date) (command.value));
		}
		return null;
	}
}

@FunctionalInterface
interface Contrast {
	<Y extends Comparable<? super Y>> Predicate test(Expression<? extends Y> x, Y y);
}
