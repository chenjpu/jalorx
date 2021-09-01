package io.jalorx.boot.model;

import io.swagger.v3.oas.annotations.media.Schema;

public abstract class LongIdVO extends BaseVO implements Id {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4948363701048760213L;
	@Schema(title = "主键ID")
	protected Long            id;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
