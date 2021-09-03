/**
 * 
 * 
 * @author ftl
 *
 */
package io.jalorx.demo.model;

import javax.validation.constraints.NotEmpty;

import io.jalorx.boot.annotation.Lookup;
import io.jalorx.boot.model.LongIdVO;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;
import io.micronaut.validation.Validated;

@Introspected
@Validated
@MappedEntity("TPL_DEMO_T")
public class Demo extends LongIdVO {

	private static final long serialVersionUID = -8274082110506731628L;

	/**
	 * 名称
	 * 
	 * @generated Tue Apr 26 09:47:46 CST 2016
	 */
	@NotEmpty
	private String name;

	/**
	 * 年龄
	 *
	 * @generated Tue Apr 26 09:47:46 CST 2016
	 */
	private Long age;

	@Lookup(type = "STATUS")
	@Transient
	private String status = "1";
	
	@Override
	public Long getId() {
		return super.getId();
	}
	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	/**
	 *
	 *
	 * @generated Tue Apr 26 09:47:46 CST 2016
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 *
	 * @generated Tue Apr 26 09:47:46 CST 2016
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 *
	 * @generated Tue Apr 26 09:47:46 CST 2016
	 */
	public Long getAge() {
		return age;
	}

	/**
	 *
	 *
	 * @generated Tue Apr 26 09:47:46 CST 2016
	 */
	public void setAge(Long age) {
		this.age = age;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
