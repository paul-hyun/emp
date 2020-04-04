/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Cache 유틸
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 11.
 * @modified 2015. 3. 11.
 * @author cchyun
 */
public class UtilCache {

	private static Map<String, Cache<Comparable<?>, Object>> cache_map = new HashMap<String, Cache<Comparable<?>, Object>>();

	public static void buildCache(String cache_key, int max, int timeout_second) {
		Cache<Comparable<?>, Object> cache = cache_map.get(cache_key);
		if (cache != null) {
			throw new RuntimeException(UtilString.format("cache already exists: {}", cache_key));
		}
		cache = CacheBuilder.newBuilder() //
				.maximumSize(max) //
				.expireAfterWrite(timeout_second, TimeUnit.SECONDS) //
				.build();
		cache_map.put(cache_key, cache);
	}

	public static Object get(String cache_key, Comparable<?> key) {
		Cache<Comparable<?>, Object> cache = cache_map.get(cache_key);
		if (cache == null) {
			throw new RuntimeException(UtilString.format("cache not exists: {}", cache_key));
		}
		return cache.getIfPresent(key);
	}

	public static ConcurrentMap<Comparable<?>, Object> getAll(String cache_key) {
		Cache<Comparable<?>, Object> cache = cache_map.get(cache_key);
		if (cache == null) {
			throw new RuntimeException(UtilString.format("cache not exists: {}", cache_key));
		}
		return cache.asMap();
	}

	public static void put(String cache_key, Comparable<?> key, Object value) {
		Cache<Comparable<?>, Object> cache = cache_map.get(cache_key);
		if (cache == null) {
			throw new RuntimeException(UtilString.format("cache not exists: {}", cache_key));
		}
		cache.put(key, value);
	}

	public static void remove(String cache_key, Comparable<?> key) {
		Cache<Comparable<?>, Object> cache = cache_map.get(cache_key);
		if (cache == null) {
			throw new RuntimeException(UtilString.format("cache not exists: {}", cache_key));
		}
		cache.invalidate(key);
	}

	public static void removeAll(String cache_key) {
		Cache<Comparable<?>, Object> cache = cache_map.get(cache_key);
		if (cache == null) {
			throw new RuntimeException(UtilString.format("cache not exists: {}", cache_key));
		}
		cache.invalidateAll();
	}

	public static void removeAll() {
		for (Entry<String, Cache<Comparable<?>, Object>> entry : cache_map.entrySet()) {
			entry.getValue().invalidateAll();
		}
	}

}
