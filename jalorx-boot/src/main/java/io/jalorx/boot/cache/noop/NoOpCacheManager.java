package io.jalorx.boot.cache.noop;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.micronaut.cache.DefaultCacheManager;
import io.micronaut.cache.SyncCache;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;

@Replaces(DefaultCacheManager.class)
@Requires(property = "noop-cache.enabled", value = StringUtils.TRUE)
@Primary
public class NoOpCacheManager implements io.micronaut.cache.CacheManager<Object> {

	private Map<String, NoOpSyncCache> cacheMap;

	/**
	 * Constructor.
	 */
	public NoOpCacheManager() {
		this.cacheMap = new ConcurrentHashMap<>();
	}

	@Override
	public Set<String> getCacheNames() {
		return cacheMap.keySet();
	}

	@Override
	public SyncCache<Object> getCache(String name) {
		NoOpSyncCache syncCache = this.cacheMap.get(name);
		if (syncCache == null) {
			syncCache = new NoOpSyncCache(name);
			this.cacheMap.put(name, syncCache);
		}
		return syncCache;
	}
}
