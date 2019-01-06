package com.dvlprofile.cacheserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dvlprofile.cacheserver.cache.CacheImpl;

@RestController
@RequestMapping("/cache")
public class CacheController {

	@RequestMapping("/test")
	public Object test() {
		return new String("Hello World");
	}
	
	@RequestMapping("/get")
	public Object get(@RequestParam("key") String key) {
		return CacheImpl.getCacheServer().get(key);
	}
	
	@RequestMapping("/put")
	@ResponseStatus(HttpStatus.CREATED)
	public void put(@RequestParam("key") String key, @RequestParam("value") String value) {
		CacheImpl.getCacheServer().put(key, value);
	}
	
	@RequestMapping("/clear")
	public void clear() {
		CacheImpl.getCacheServer().clear();
	}
	
	@RequestMapping("/size")
	public Object size() {
		return CacheImpl.getCacheServer().size();
	}
	
	@RequestMapping("/remove")
	public void remove(@RequestParam("key") String key) {
		CacheImpl.getCacheServer().remove(key);
	}
}
