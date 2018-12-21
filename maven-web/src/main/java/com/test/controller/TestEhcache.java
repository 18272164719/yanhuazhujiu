package com.test.controller;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.List;

public class TestEhcache {

    public static void main(String[] args) {

        CacheManager cacheManager = CacheManager.create("D:\\WORKSOFT\\shyldemo\\nice-to-meet-you\\maven-web\\src\\main\\resources\\spring-shiro-ehcache.xml");
        Cache cache = cacheManager.getCache("shiro-activeSessionCache");
        List keys = cache.getKeys();
        for(Object k : keys){
            System.out.println(k.toString());
        }
        System.out.println(cache);
    }
}
