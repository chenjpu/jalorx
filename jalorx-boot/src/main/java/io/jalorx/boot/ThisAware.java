/**
 * 
 */
package io.jalorx.boot;

/**
 * Service接口AOP基于Proxy实现，
 * 
 * 当需要在Service实现中访问兄弟方法的时候，如果直接通过this访问，AOP不会拦截，通过实现该接口，提供增强后的当前对象（即Proxy实例）
 * 
 * @author chenb
 *
 */
public interface ThisAware {

	/**
	 * 有框架启动的时候设置该值
	 * 
	 * @param o
	 */
	void setThis(Object o);

}
