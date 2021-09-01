/**
 * 
 */
package io.jalorx.boot;

import java.util.List;

/**
 * 工作流相关的参数对象类
 * 
 * @author chenb
 *
 */
public class Flow {

	/**
	 * 任务id
	 */
	private String taskId;
	/**
	 * 流程定义id
	 */
	private String processDefId;
	/**
	 * 流程实例id
	 */
	private String processId;
	/**
	 * 路由id
	 */
	private String sequenceFlowId;

	// 路由名称
	private String sequenceFlowName;

	public String getSequenceFlowName() {
		return sequenceFlowName;
	}

	public void setSequenceFlowName(String sequenceFlowName) {
		this.sequenceFlowName = sequenceFlowName;
	}

	/**
	 * 名称
	 */
	private String       processName;
	/**
	 * 状态
	 */
	private String       processStatus;
	/**
	 * 意见
	 */
	private String       handSuggest;
	/**
	 * 抄送人名字
	 */
	private List<String> ccNames;

	public List<String> getCcNames() {
		return ccNames;
	}

	public void setCcNames(List<String> ccNames) {
		this.ccNames = ccNames;
	}

	/**
	 * 抄送人ID
	 */
	private List<Long> cc;

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the processDefId
	 */
	public String getProcessDefId() {
		return processDefId;
	}

	/**
	 * @param processDefId the processDefId to set
	 */
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @return the sequenceFlowId
	 */
	public String getSequenceFlowId() {
		return sequenceFlowId;
	}

	/**
	 * @param sequenceFlowId the sequenceFlowId to set
	 */
	public void setSequenceFlowId(String sequenceFlowId) {
		this.sequenceFlowId = sequenceFlowId;
	}

	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @return the processStatus
	 */
	public String getProcessStatus() {
		return processStatus;
	}

	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getHandSuggest() {
		return handSuggest;
	}

	public void setHandSuggest(String handSuggest) {
		this.handSuggest = handSuggest;
	}

	public List<Long> getCc() {
		return cc;
	}

	public void setCc(List<Long> cc) {
		this.cc = cc;
	}

}
