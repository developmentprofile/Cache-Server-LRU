package com.dvlprofile.cacheserver.cache;

import com.dvlprofile.cacheserver.model.CachableHashMap;


public class CacheImpl implements Cache{
	private static CacheImpl cacheServer;
	private final static int CACHE_REFRESH_INSEC = 5;
	
	private final static CachableHashMap cache = new CachableHashMap(10);
	
	@Override
	public void put(String key, String value) {
		if (key == null || value == null)
			return;
		
		cache.put(key, value, System.currentTimeMillis() + 30000);
	}

	@Override
	public Object get(String key) {
		if (key == null) return null;
		
		return cache.get(key);
	}

	@Override
	public void remove(String key) {
		if (key == null) return;
		
		cache.remove(key);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public int size() {
		return cache.size();
	}

	public CacheImpl() {
		Thread cacheRefresh = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
		            Thread.sleep(CACHE_REFRESH_INSEC * 1000);
		            this.cache.removeExpired();
		        } catch (InterruptedException e) {
		            Thread.currentThread().interrupt();
		        }
			}
		});
		
		cacheRefresh.setDaemon(true);
		cacheRefresh.start();
	}
	
	public static CacheImpl getCacheServer() {
		if (cacheServer == null)
			cacheServer =  new CacheImpl();
		
		return cacheServer;
	}
	
}
