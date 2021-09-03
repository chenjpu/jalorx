package io.jalorx.boot.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.swagger.v3.oas.annotations.media.Schema;


@Introspected
public abstract class LongIdVO extends BaseVO implements Id<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4948363701048760213L;
	@Schema(title = "主键ID")
	@GeneratedValue
	@io.micronaut.data.annotation.Id
	private Long id;

	public LongIdVO() {
	}

	public LongIdVO(Long id) {
		this.id = id;
	}

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
