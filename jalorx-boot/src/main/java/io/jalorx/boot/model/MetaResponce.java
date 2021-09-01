package io.jalorx.boot.model;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.jalorx.boot.POJO;
import io.micronaut.core.annotation.Introspected;

/**
 * 带元数据的响应对象
 * 
 * @author chenb
 *
 * @param <T>
 */
@Introspected
public class MetaResponce<T> implements POJO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5980736483715959504L;

	private final T data;

	private final Map<String, List<Id>> meta = new HashMap<>();

	protected MetaResponce(T data) {
		this.data = data;
	}

	protected MetaResponce() {
		this(null);
	}

	public T getData() {
		return data;
	}

	public Map<String, List<Id>> getMeta() {
		return meta;
	}

	public static <T> MetaResponce<T> ok() {
		return new MetaResponce<>();
	}

	public static <T> MetaResponce<T> ok(T data) {
		return new MetaResponce<>(data);
	}

	public MetaResponce<T> meta(Class<? extends Annotation> key, Id... values) {
		return meta(key.getSimpleName(), values);
	}

	public MetaResponce<T> meta(Class<? extends Annotation> key, Iterable<? extends Id> values) {
		return meta(key.getSimpleName(), values);
	}

	public MetaResponce<T> meta(String key, Id... values) {
		List<Id> list = this.meta.computeIfAbsent(key, k -> new LinkedList<>());
		for (Id e : values) {
			list.add(e);
		}
		return this;
	}

	public MetaResponce<T> meta(String key, Iterable<? extends Id> values) {
		List<Id> list = this.meta.computeIfAbsent(key, k -> new LinkedList<>());
		for (Id e : values) {
			list.add(e);
		}
		return this;
	}
}
