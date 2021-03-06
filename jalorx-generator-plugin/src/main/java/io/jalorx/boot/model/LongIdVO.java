package io.jalorx.boot.model;

public abstract class LongIdVO extends BaseVO implements Id<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4948363701048760213L;
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
