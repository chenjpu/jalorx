package io.jalorx.boot;

import java.io.Serializable;

/**
 * 实体基础接口
 * 
 * @author chenb
 * 
 */
public interface POJO extends Serializable {

	//创建初始化
	default void createInit() {}

	//修改初始化
	default void updateInit() {

	}
}
