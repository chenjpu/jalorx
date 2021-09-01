package io.jalorx.boot.model;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author chenbing 组织模型
 */
public abstract class CommonVO extends LongIdVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5505906463885538098L;

	@Schema(title = "名称")
	@NotEmpty
	private String name; // 名称
	@Schema(title = "编码")
	@NotEmpty
	private String code; // 编码
	@Schema(title = "描述")
	@NotEmpty
	private String desp;// 描述
	@Schema(title = "显示序列号")
	private int    order; // 显示序号

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String description) {
		this.desp = description;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
