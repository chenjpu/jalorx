package io.jalorx.boot.errors;

import io.jalorx.boot.BusinessAccessException;

/**
 * 错误常量
 * 
 * A+4d：用户端错误 B+4d：服务端错误 C+4d：第三方调用错误
 * 
 * @author chenb
 *
 */

public final class ErrCode {

	// private static final Map<String, ErrCode> BY_CODE = new HashMap<>(24);

	public final static ErrCode OK      = new ErrCode("00000", "Ok");
	public final static ErrCode UNKNOWN = new ErrCode("99999", "Unknown");
	// 1.用户端错误 A+ddddd
	public final static ErrCode A_ERR                       = new ErrCode("A0001", "用户端错误");
	public final static ErrCode A_REGISTER_ERROR            = new ErrCode("A0100", "用户注册错误");
	public final static ErrCode A_NAME_CHECK_ERROR          = new ErrCode("A0110", "用户名校验失败");
	public final static ErrCode A_NAME_EXISTING             = new ErrCode("A0111", "用户名已存在");
	public final static ErrCode A_LOGIN_ANOMALY             = new ErrCode("A0200", "用户登陆异常");
	public final static ErrCode A_NO_ACCOUNT                = new ErrCode("A0201", "用户账户不存在");
	public final static ErrCode A_FROZEN_ACCOUNT            = new ErrCode("A0202", "用户账户被冻结");
	public final static ErrCode A_CANCELLED_ACCOUNT         = new ErrCode("A0203", "用户账户已作废");
	public final static ErrCode A_PASSWORD_ERROR            = new ErrCode("A0210", "用户密码错误");
	public final static ErrCode A_PASSWORD_EXCEEDING_LIMITS = new ErrCode("A0211", "用户输入密码次数超限");
	public final static ErrCode A_IDENTITY_CHECK_FAILED     = new ErrCode("A0220", "用户身份校验失败");
	public final static ErrCode A_ACCOUNT_EXPIRED           = new ErrCode("A0221", "用户已过期");
	public final static ErrCode A_PASSWORD_EXPIRED          = new ErrCode("A0222", "密码已过期");
	public final static ErrCode A_AUTHORITY_ERROR           = new ErrCode("A0300", "访问权限异常");
	public final static ErrCode A_UNAUTHORIZED              = new ErrCode("A0301", "访问未授权");
	public final static ErrCode A_LOGIN_EXPIRED             = new ErrCode("A0230", "用户登陆已过期");
	public final static ErrCode A_BAD_REQUEST               = new ErrCode("A0400", "用户请求参数错误");
	public final static ErrCode A_INVALID_INPUT             = new ErrCode("A0402", "无效的用户输入");
	public final static ErrCode A_PARAMETERS_OUTOF_RANGE    = new ErrCode("A0420", "请求参数值超出允许的范围");
	public final static ErrCode A_PARAMETER_FORMAT_MISMATCH = new ErrCode("A0421", "参数格式不匹配");
	public final static ErrCode A_NUMBER_EXCEEDING_LIMITS   = new ErrCode("A0425", "数量超出限制");
	public final static ErrCode A_BATCH_NUMBER_LIMITS       = new ErrCode("A0426", "请求批量处理总个数超出限制");
	public final static ErrCode A_JSON_PARSING_FAILURE      = new ErrCode("A0427", "请求JSON解析失败");
	public final static ErrCode A_ILLEGAL_INPUT_CONTENT     = new ErrCode("A0430", "用户输入内容非法");
	public final static ErrCode A_EXIST_LINKED_DATA         = new ErrCode("A0431", "存在关联数据，无法删除");
	public final static ErrCode A_DSL_NOEXIST               = new ErrCode("A0500", "不存在查询字段");

	public final static ErrCode A_UNKNOWN = new ErrCode("A9999", "用户未知错误");

	// 2.服务端错误B+ddddd
	public final static ErrCode B_EXEC_ERROR            = new ErrCode("B0001", "系统执行出错");
	public final static ErrCode B_EXEC_TIMED_OUT        = new ErrCode("B0100", "系统执行超时");
	public final static ErrCode B_CURRENT_LIMIT         = new ErrCode("B0210", "系统限流");
	public final static ErrCode B_DEMOTION              = new ErrCode("B0220", "系统功能降级");
	public final static ErrCode B_RESOURCE_ERROR        = new ErrCode("B0300", "系统资源异常");
	public final static ErrCode B_DEPLETED_DS           = new ErrCode("B0311", "系统磁盘空间耗尽");
	public final static ErrCode B_DEPLETED_NN           = new ErrCode("B0312", "系统内存耗尽");
	public final static ErrCode B_DEPLETED_FH           = new ErrCode("B0313", "文件句柄耗尽");
	public final static ErrCode B_DEPLETED_CP           = new ErrCode("B0314", "系统连接池耗尽");
	public final static ErrCode B_DEPLETED_TP           = new ErrCode("B0315", "系统线程池耗尽");
	public final static ErrCode B_RESOURCE_ACCESS_ERROR = new ErrCode("B0320", "系统资源访问异常");
	public final static ErrCode B_READ_DISK_FILE_ERROR  = new ErrCode("B0321", "系统读取磁盘文件失败");
	public final static ErrCode B_UNKNOWN               = new ErrCode("B9999", "系统未知错误");

	// 3.第三方调用错误 C+dddd
	public final static ErrCode C_RPC_ERROR     = new ErrCode("C0001", "调用第三方服务出错");
	public final static ErrCode C_RPC_TIMED_OUT = new ErrCode("C0200", "第三方系统执行超时");
	public final static ErrCode C_UNKNOWN       = new ErrCode("C9999", "第三方调用未知错误");

	private final String code;
	private final String message;

	/**
	 * @param code The code
	 * @param message The message
	 */
	public ErrCode(String code, String message) {
		this.code    = code;
		this.message = message;
		// BY_CODE.put(code, this);
	}

	/**
	 * @return The message text
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return The code
	 */
	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + " - " + message;
	}

	public BusinessAccessException wrap(Throwable ex, Object... args) {
		return new BusinessAccessException(this, ex, args);
	}

	public BusinessAccessException wrap(Object... args) {
		return new BusinessAccessException(this, args);
	}
}
