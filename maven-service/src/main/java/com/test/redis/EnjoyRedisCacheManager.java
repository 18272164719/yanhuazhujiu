package com.test.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Collection;

public class EnjoyRedisCacheManager extends RedisCacheManager{

    private final static Logger logger = LoggerFactory.getLogger(EnjoyRedisCacheManager.class);

    /**
     * 缓存参数的分隔符
     * 数组元素0=缓存的名称
     * 数组元素1=缓存过期时间TTL
     * 数组元素2=缓存在多少秒开始主动失效来强制刷新
     */
    private String separator = "#";
    /**
     * 缓存主动在失效前强制刷新缓存的时间
     * 单位：秒
     */
    private long preloadSecondTime=0;

    public EnjoyRedisCacheManager(RedisTemplate template, Collection<String> cacheNames) {
        super(template, cacheNames);
    }

    /**
     * 重写getCache方法
     * @param name
     * @return
     */
    @Override
    public Cache getCache(String name) {

        String[] cacheParams=name.split(this.getSeparator());

        String cacheName = cacheParams[0];
        if(StringUtils.isBlank(cacheName)){
            return null;
        }

        /*Long expirationSecondTime = this.computeExpiration(cacheName);
        if(cacheParams.length > 1){
            expirationSecondTime=Long.parseLong(cacheParams[1]);
            this.setDefaultExpiration(expirationSecondTime);
        }
        if(cacheParams.length > 2){
            this.setPreloadSecondTime(Long.parseLong(cacheParams[2]));
        }
        Cache cache = super.getCache(cacheName);
        if(null == cache){
            return cache;
        }*/
        /*EnjoyRedisCacheManager redisCache = new EnjoyRedisCacheManager(cacheName,
                (this.isUsePrefix() ? this.getCachePrefix().prefix(cacheName) : null),
                this.getRedisOperations(),
                expirationSecondTime,
                preloadSecondTime);*/

        return null;
    }


    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public long getPreloadSecondTime() {
        return preloadSecondTime;
    }

    public void setPreloadSecondTime(long preloadSecondTime) {
        this.preloadSecondTime = preloadSecondTime;
    }
}
