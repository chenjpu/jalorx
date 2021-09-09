/**
 * 
 */
package io.jalorx.boot.service;

import java.io.Serializable;
import java.util.List;

import io.jalorx.boot.TreeNode;
import io.jalorx.boot.datasource.annotation.DS;
import io.micronaut.transaction.annotation.ReadOnly;

/**
 * 异步级联选择服务
 * 
 * @author chenb
 *
 */
public interface CascaderService<ID extends Serializable> {

	/**
	 * 指定父id查询所有的直接子节点
	 * 
	 * @param pId
	 * @return
	 */
	@DS.Slave
	@ReadOnly
	List<TreeNode> getChildrenByPId(ID pId);

	/**
	 * 通过以选择的节点，初始化树结构
	 * 
	 * @param ids
	 * @return
	 */
	@DS.Slave
	@ReadOnly
	List<TreeNode> getAllByIds(ID[] ids);
}
