package io.jalorx.boot;

/**
 * 树形结构的业务对象抽象接口
 * 
 * @author sa
 *
 */
public interface TreeEntity extends POJO {

	/**
	 * 
	 * @return 父对象
	 */
	public abstract TreeEntity getParent();
}
