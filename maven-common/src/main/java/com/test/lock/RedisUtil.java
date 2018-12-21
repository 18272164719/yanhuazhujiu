package com.test.lock;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisUtil {


    public static JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(50);
        config.setMinIdle(20);
        config.setMaxTotal(100);
        config.setMaxWaitMillis(100);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        JedisPool jedisPool = new JedisPool(config, "192.168.11.190", 6379);
        return jedisPool;
    }

    public static Jedis getJedis() {
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisConnectionException e) {
            jedisPool.returnBrokenResource(jedis);
        }
        return jedis;
    }

    public static RedisTemplate getRedisTemplate(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(50);
        config.setMinIdle(20);
        config.setMaxTotal(100);
        config.setMaxWaitMillis(100);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(config);
        connectionFactory.setHostName("192.168.11.200");
        connectionFactory.setPort(6379);
        connectionFactory.afterPropertiesSet();
        RedisTemplate<Serializable, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    public static void main(String[] args) {
        /*List<String> s = new ArrayList<>();
        s.add("a");
        List<String> f = new ArrayList<>();
        f.add("b");*/
        RedisTemplate redisTemplate = getRedisTemplate();
        /*redisTemplate.opsForList().leftPush("t",s);
        redisTemplate.opsForList().leftPush("t",f);
        redisTemplate.expire("t",5, TimeUnit.SECONDS);
        *//*redisTemplate.opsForList().leftPush("t",f);*//*
        //redisTemplate.delete("t");
        List<String> list = (List<String>)redisTemplate.opsForList().leftPop("t");
        for(String str : list){productList
            System.out.println(str);
        }*/
        //System.out.println(list.toArray());
        //redisTemplate.opsForList().leftPop("productList0");
    }

}
