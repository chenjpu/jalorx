package io.jalorx.boot.cache.noop;

import java.util.Optional;
import java.util.function.Supplier;

import io.micronaut.cache.SyncCache;
import io.micronaut.core.type.Argument;

public class NoOpSyncCache implements SyncCache<Object> {

	private String name;

	/**
	 * Constructor.
	 *
	 * @param name the cache name
	 */
	public NoOpSyncCache(String name) {
		this.name = name;
	}

	@Override
	public <T> Optional<T> get(Object key, Argument<T> requiredType) {
		return Optional.empty();
	}

	@Override
	public <T> T get(Object key, Argument<T> requiredType, Supplier<T> supplier) {
		return supplier.get();
	}

	@Override
	public <T> Optional<T> putIfAbsent(Object key, T value) {
		return Optional.of(value);
	}

	@Override
	public void put(Object key, Object value) {}

	@Override
	public void invalidate(Object key) {}

	@Override
	public void invalidateAll() {}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getNativeCache() {
		return this;
	}
}
