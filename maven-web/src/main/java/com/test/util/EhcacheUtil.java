package com.test.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;

public class EhcacheUtil implements InitializingBean {

    private EhCacheCacheManager ehCacheCacheManager;
    private CacheManager manager;

    private static Logger logger = LoggerFactory.getLogger(EhcacheUtil.class);


    public void put(String cacheName, Object key, Object value) {
        logger.info("EhcacheUtil.name=====" + cacheName);
        Cache cache = this.manager.getCache(cacheName);
        if (cache == null) {
            cache = new Cache(cacheName, 10000, false, false, 86400L, 3600L);
            this.manager.addCache(cache);
        }

        Element element = new Element(key, value);
        cache.put(element);
    }

    public Object get(String cacheName, Object key) {
        Cache cache = this.manager.getCache(cacheName);
        if (cache != null) {
            Element element = cache.get(key);
            return element == null ? null : element.getObjectValue();
        } else {
            return null;
        }
    }

    public Cache get(String cacheName) {
        return this.manager.getCache(cacheName);
    }

    public void remove(String cacheName, Object key) {
        Cache cache = this.manager.getCache(cacheName);
        if (cache != null) {
            cache.remove(key);
        }

    }

    public void afterPropertiesSet() throws CacheException {
        this.manager = this.ehCacheCacheManager.getCacheManager();
    }

    public EhCacheCacheManager getEhCacheCacheManager() {
        return ehCacheCacheManager;
    }

    public void setEhCacheCacheManager(EhCacheCacheManager ehCacheCacheManager) {
        this.ehCacheCacheManager = ehCacheCacheManager;
    }
}
