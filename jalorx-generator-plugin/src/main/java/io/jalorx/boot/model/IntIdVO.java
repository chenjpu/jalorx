package io.jalorx.boot.model;


public abstract class IntIdVO extends BaseVO implements Id<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4948363701048760213L;
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
