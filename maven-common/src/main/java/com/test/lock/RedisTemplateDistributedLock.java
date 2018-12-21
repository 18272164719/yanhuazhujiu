package com.test.lock;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * redis 分布式锁  性能高  用 redisTemplate  提供Redis一些不直接支持的原子性的操作,很多实现采用了lua脚本
 * <p>
 * 实现3种方式  获取锁
 * 1、尝试获取锁 超时返回
 * 2、尝试获取锁  立即返回
 * 3、以阻塞的方式获取锁
 */
public class RedisTemplateDistributedLock implements Lock {

    private static Logger logger = LoggerFactory.getLogger(RedisTemplateDistributedLock.class);

    private RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 锁信息的上下文,保存当前锁的持有人的id
     */
    private ThreadLocal<String> lockContext = new ThreadLocal<>();

    /**
     * 将key 的值设为value ，当且仅当key 不存在，等效于 SETNX。
     */
    public static final String NX = "NX";
    /**
     * seconds — 以秒为单位设置 key 的过期时间，等效于EXPIRE key seconds
     */
    public static final String EX = "EX";
    /**
     * 调用set后的返回值
     */
    public static final String OK = "OK";
    /**
     * 默认请求锁的超时时间(毫秒  ms)
     */
    private final long TIMEOUT = 100;

    /**
     * 默认请求锁的有效期(秒 s)
     */
    private final long EXPIERTIME = 60;

    /**
     * 解锁的lua脚本
     */
    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    /**
     * 锁标志对应的key
     */
    private String lockKey;

    /**
     * 请求锁的超时时间(ms)
     */
    private long timeOut = TIMEOUT;
    /**
     * 存储当前线程
     */
    private Thread exclusiveOwnerThread;

    public RedisTemplateDistributedLock(RedisTemplate<Serializable, Object> redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
    }


    /**
     * 该方式是以阻塞的方式获取锁   拿不到就一直等待
     */
    @Override
    public void lock() {
        while (!tryLock()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean tryLock() {
        return tryLock(EXPIERTIME, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        String id = UUID.randomUUID().toString();
        Thread t = Thread.currentThread();
        try {
            if (OK.equalsIgnoreCase(set(lockKey, id, time))) {
                logger.info("获取分布式锁成功");
                lockContext.set(id);
                setExclusiveOwnerThread(t);
                return true;
            } else if (exclusiveOwnerThread == t) {  //当前线程已经获得了锁，可重入
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 尝试获取锁， 超时返回
     */
    public boolean lockReturnBack() {
        String id = UUID.randomUUID().toString();
        Thread t = Thread.currentThread();
        long timeOut = TIMEOUT * 1000000;
        long nowTime = System.nanoTime();
        while ((System.nanoTime()) - nowTime < timeOut) {
            try{
                if (OK.equalsIgnoreCase(set(lockKey, id, EXPIERTIME))) {
                    logger.info("获取分布式锁成功");
                    lockContext.set(id);
                    setExclusiveOwnerThread(t);
                    return true;
                } else if (exclusiveOwnerThread == t) {//当前线程已经获得了锁，可重入
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 尝试获取锁，立即返回
     */
    public boolean lockRetuanQuick() {
        String id = UUID.randomUUID().toString();
        return OK.equalsIgnoreCase(set(lockKey, id, EXPIERTIME));
    }


    @Override
    public void unlock() {
        tryUnlock();
    }

    public Boolean tryUnlock() {
        try {
            if (lockContext.get() == null) {
                return false;
            }
            return redisTemplate.execute((RedisConnection connection) -> {
                Object nativeConnection = connection.getNativeConnection();
                Long result = 0l;
                //集群模式
                /*if(nativeConnection instanceof JedisCluster){
                    result = (Long)((JedisCluster) nativeConnection).eval(UNLOCK_LUA, Arrays.asList(lockKey), Arrays.asList(lockContext.get()));
                }*/
                if (nativeConnection instanceof Jedis) {
                    result = (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, Arrays.asList(lockKey), Arrays.asList(lockContext.get()));
                }
                //单机模式
                if (result == 0 && lockContext.get() != null) {
                    logger.debug("redis 分布式锁 解锁失败" + System.currentTimeMillis());
                }
                if (result == 1) {
                    logger.info("解锁成功");
                    lockContext.remove();
                    return true;
                }
                return false;
            });
        } catch (Exception e) {
            logger.warn("Redis不支持EVAL命令，使用降级方式解锁：{}", e.getMessage());
            String value = this.get(lockKey, String.class);
            if (lockContext.get().equals(value)) {
                redisTemplate.delete(lockKey);
                return true;
            }
        }
        return false;
    }

    /**
     * 重写redisTemplate的set方法
     * 命令 SET resource-name anystring NX EX max-lock-time 是一种在 Redis 中实现锁的简单方法
     * 如果服务器返回 OK ，那么这个客户端获得锁。
     * 如果服务器返回 NIL ，那么客户端获取锁失败，可以在稍后再重试
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    private String set(final String key, final String value, final long seconds) {

        Assert.isTrue(!StringUtils.isEmpty(key), "key不能为空");
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                String result = null;
                if (nativeConnection instanceof JedisCommands) {
                    result = ((JedisCommands) nativeConnection).set(key, value, NX, EX, seconds);
                }
                if (!StringUtils.isEmpty(lockContext.get()) && !StringUtils.isEmpty(result)) {
                    logger.info("获取锁{}的时间：{}", lockContext.get(), System.currentTimeMillis());
                }
                return result;
            }
        });
    }

    private <T> T get(final String key, Class<T> tClass) {
        Assert.isTrue(!StringUtils.isEmpty(key), "key不能为空");
        return redisTemplate.execute((RedisConnection connection) -> {
            Object nativeConnection = connection.getNativeConnection();
            Object result = null;
            if (nativeConnection instanceof JedisCommands) {
                result = ((JedisCommands) nativeConnection).get(key);
            }
            return (T) result;
        });
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }

    public RedisTemplate<Serializable, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

}
