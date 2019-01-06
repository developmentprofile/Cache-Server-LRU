package com.dvlprofile.cacheserver.model;

public class CachableClass {
	
	private String key;
	private String cacheVal; 
	private long expireTime;
	private int usage = 0;
	
	public CachableClass(String cacheVal, long expireTime) {
		super();
		this.cacheVal = cacheVal;
		this.expireTime = expireTime;
	}
	public String getCacheVal() {
		return cacheVal;
	}
	public void setCacheVal(String cacheVal) {
		this.cacheVal = cacheVal;
	}
	public long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	
	public int getUsage() {
		return usage;
	}
	public void setUsage(int usage) {
		this.usage = usage;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isExpired() {
		return System.currentTimeMillis() > expireTime;
	}
	
	
}
