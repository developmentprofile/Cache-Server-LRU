package com.dvlprofile.cacheserver.model;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CachableHashMap{
	
	private int maxSize;
	private ConcurrentHashMap<String, SoftReference<CachableClass>> cache = new ConcurrentHashMap<>();
	private HashMap<String, Integer> usages;
	private HashMap<Integer, LinkedHashSet<String>> listOfList;
	private int minFreq;
	
	public CachableHashMap(int size) {
		super();
		this.maxSize = size;
		usages = new HashMap<>();
		listOfList = new HashMap<>();
		listOfList.put(1, new LinkedHashSet<String>());
		minFreq = 0;
	}
	
	public void put(String key, String value, long expireTime) {
		
		if (cache.containsKey(key)) {
			cache.put(key, new SoftReference<CachableClass>(new CachableClass(value, expireTime)));
            get(key);
            return;
		}
		if (cache.size() > maxSize) {
			// Get Key First Element from MinFrequency List
			String removeItemKey = listOfList.get(minFreq).iterator().next();
			cache.remove(removeItemKey);
			usages.remove(key);	
		}
		cache.put(key, new SoftReference<CachableClass>(new CachableClass(value, expireTime)));
		usages.put(key, 1);
		minFreq = 1;
		listOfList.get(1).add(key);
	}
	
	public Object get(String key) {
		// increase frequency
		int counter = usages.get(key);
		usages.put(key, counter+1);
		listOfList.get(counter).remove(key);
		
		// If no item is left on that frequency, minumum frequency is renewed
		if (counter == minFreq && listOfList.get(counter).size() == 0)
			minFreq++;
		
		if (!listOfList.containsKey(counter + 1))
			listOfList.put(counter + 1, new LinkedHashSet<String>());
		listOfList.get(counter + 1).add(key);
		
		return cache.get(key).get().getCacheVal();
		
	}
	
	public void remove(String key) {
		int counter = usages.get(key);
		usages.remove(key);
		listOfList.get(counter).remove(key);
		cache.remove(key);
	}
	
	public int size() {
		return cache.size();
	}
	
	public void clear() {
		usages.clear();
		listOfList.clear();
		cache.clear();
	}
	
	public void removeExpired() {
		cache.entrySet().removeIf(entry -> Optional.ofNullable(
				entry.getValue()).map(SoftReference::get).map(CachableClass::isExpired).orElse(false));
	}
	
}
