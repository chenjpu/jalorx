/**
 * 
 */
package io.jalorx.boot.service.impl;

import io.jalorx.boot.ThisAware;

/**
 * Proxy代理模式下,返回当前对象的代理对象
 * 
 * @author chenb
 *
 */
public abstract class BaseThisAware implements ThisAware {

	private Object self;

	public BaseThisAware() {
		this.self = this;
	}

	public void setThis(Object self) {
		this.self = self;
	}

	@SuppressWarnings("unchecked")
	protected <S> S getThis() {
		return (S) self;
	}

}
