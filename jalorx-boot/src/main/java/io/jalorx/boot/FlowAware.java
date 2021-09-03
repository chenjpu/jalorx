/**
 * 
 */
package io.jalorx.boot;

import io.jalorx.boot.model.Id;

/**
 * 流程关注接口，
 * 
 * 具备流程操作的业务对象都需要集成该接口
 * 
 * @author chenb
 *
 */
public interface FlowAware extends Id<Long> {

	/**
	 * 返回流程信息对象
	 * 
	 * @return 流程对象
	 */
	Flow getFlow();

	/**
	 * 设置流程实例名称
	 * 
	 * @param name 流程实例的名称或者标题
	 */
	void setProcessName(String name);

	/**
	 * 设置流程实例状态
	 * 
	 * @param status 流程的业务状态
	 */
	void setProcessStatus(String status);

	/**
	 * 获取草稿ID
	 * 
	 * @return 草稿id
	 */
	Long getDfId();
}
