package io.jalorx.boot;

import io.jalorx.boot.sql.Command;

/**
 * 行级权限规则接口
 * 
 * @author chenb
 *
 */
public interface RowRule {

	RowRule DENY_ALL = new RowRule() {

		@Override
		public String getCode() {
			return "DENY_ALL";
		}

		@Override
		public Command toCommand(String field) {
			return Command.DENY_ALL;
		}

		@Override
		public boolean isALL() {
			return false;
		}
	};

	/**
	 * 编码
	 * 
	 * @return
	 */
	String getCode();

	/**
	 * @param field
	 * @return
	 */
	Command toCommand(String field);

	/**
	 * 是否所有权限
	 * 
	 * @return
	 */
	boolean isALL();
}
