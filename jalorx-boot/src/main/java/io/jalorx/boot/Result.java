package io.jalorx.boot;

/**
 * 结果集返回接口
 * 
 * @author administration
 *
 */
public interface Result extends POJO {

	boolean getSuccess();// 是否成功

	String getErrors();// 错误消息

}
