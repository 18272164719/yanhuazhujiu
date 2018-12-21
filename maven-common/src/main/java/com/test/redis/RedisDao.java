package com.test.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redisTemplate.opsForValue();  操作字符串
 * redisTemplate.opsForHash();  操作hash
 * redisTemplate.opsForList();  操作list
 * redisTemplate.opsForSet();  操作set
 * redisTemplate.opsForZSet();  操作有序set
 * @return
 */

public class RedisDao implements IRedisDao{

    private RedisTemplate<Serializable, Object> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RedisDao.class);

    @Override
    public Object getNode(String key) {
        ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
        return operations.get(key);
    }

    @Override
    public Object get(String key) {

        return null;
    }

    @Override
    public boolean setNode(String key, Object value) {
        boolean result = false;
        try{
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            operations.set(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        try{
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            operations.set(key,value);
            this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void remove(String... keys) {
        for(String s : keys){
            this.remove(s);
        }
    }

    @Override
    public void remove(String key) {
        if(this.exists(key)){
            this.redisTemplate.delete(key);
        }
    }

    @Override
    public void removePattern(String pattern) {
        Set<Serializable> keys = this.redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            this.redisTemplate.delete(keys);
        }
    }

    @Override
    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key).booleanValue();
    }

    @Override
    public long increment(String key, long delta) {
        return this.redisTemplate.opsForValue().increment(key,delta).longValue();
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
