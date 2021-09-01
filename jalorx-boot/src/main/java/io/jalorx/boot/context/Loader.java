package io.jalorx.boot.context;

/**
 * 资源加载
 * 
 * @author chenb
 *
 * @param <T>
 */
public interface Loader<T> {
	T load();
}
