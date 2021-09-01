package io.jalorx.boot.mybatis.binding;

import java.util.Objects;

import io.micronaut.inject.ExecutableMethod;

/**
 * Key used to cache results for repository method invocations.
 * 
 * @author chenb
 *
 */
public final class MapperMethodKey {
	private final Object                 repository;
	private final ExecutableMethod<?, ?> method;
	private final int                    hashCode;

	/**
	 * Default constructor.
	 * 
	 * @param repository The repository
	 * @param method The method
	 */
	public MapperMethodKey(Object repository, ExecutableMethod<?, ?> method) {
		this.repository = repository;
		this.method     = method;
		this.hashCode   = Objects.hash(repository, method);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MapperMethodKey that = (MapperMethodKey) o;
		return repository.equals(that.repository) && method.equals(that.method);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}
}
