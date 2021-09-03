package io.jalorx.boot.model;

import io.micronaut.data.annotation.GeneratedValue;
import io.swagger.v3.oas.annotations.media.Schema;

public abstract class IntIdVO extends BaseVO implements Id<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4948363701048760213L;
	@Schema(title = "主鍵ID")
	@GeneratedValue
    @io.micronaut.data.annotation.Id
	protected Integer         id;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
