/*
 * 
 * 创建日期：2010-4-10 下午09:32:00
 *
 * 创 建 人 ：chenjpu
 * 
 * 版权所有：J.Bob
 */

package io.jalorx.boot.dao;

import io.jalorx.boot.POJO;

/**
 * 组件类型为long的抽象数据访问接口
 * 
 * @author chenb
 *
 * @param <T> 业务对象类型
 */
public interface BaseDao<T extends POJO> extends GenericDao<T, Long> {

}
