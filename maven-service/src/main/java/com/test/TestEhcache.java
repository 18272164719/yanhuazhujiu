package com.test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class TestEhcache {

    public static void main(String[] args) {

        CacheManager cacheManager = CacheManager.create("D:\\WORKSOFT\\shyldemo\\newrestaurant\\maven-service\\src\\main\\resources\\ehcache.xml");
        Cache cache = cacheManager.getCache("user");
        Element user = cache.get("#user1");
        System.out.println(user);
    }
}
