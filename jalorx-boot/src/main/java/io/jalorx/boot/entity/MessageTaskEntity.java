package io.jalorx.boot.entity;

import java.util.List;

import io.jalorx.boot.POJO;

public class MessageTaskEntity implements POJO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 流程定义id
	 */
	private String processDefinitionId;

	/**
	 * 流程实例id
	 */
	private String processInstanceId;

	private String taskName;

	private String processName;

	/**
	 * 流程发起人id
	 */
	private String startUserId;

	/**
	 * 流程发起人收件人信息
	 */
	private String toStartUserMails;

	private String businessKey;

	/**
	 * 流程处理人信息
	 */
	private String handler;

	/**
	 * 处理人收件人信息
	 */
	private List<String> toHandlerMails;

	/**
	 * 抄送人信息
	 */
	private List<String> toCcMails;

	/**
	 * 流程是否结束
	 */
	private boolean isEnd;

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getToStartUserMails() {
		return toStartUserMails;
	}

	public void setToStartUserMails(String toStartUserMails) {
		this.toStartUserMails = toStartUserMails;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public List<String> getToHandlerMails() {
		return toHandlerMails;
	}

	public void setToHandlerMails(List<String> toHandlerMails) {
		this.toHandlerMails = toHandlerMails;
	}

	public List<String> getToCcMails() {
		return toCcMails;
	}

	public void setToCcMails(List<String> toCcMails) {
		this.toCcMails = toCcMails;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

}
