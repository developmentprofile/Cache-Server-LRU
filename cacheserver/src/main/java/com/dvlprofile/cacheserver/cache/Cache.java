package com.dvlprofile.cacheserver.cache;

public interface Cache {
	
	public void put(String key, String value);
	
	public Object get(String key);
	
	public void remove(String key);
	
	public void clear();
	
	public int size();
	
	
}
