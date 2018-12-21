package com.test.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * redis 分布式锁  性能高  用 jedis
 */

public class RedisDistributedLock implements Lock {

    private static Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    //锁信息的上下文,保存当前锁的持有人的id
    private ThreadLocal<String> lockContext = new ThreadLocal<>();

    //默认锁的有效期
    private long time = 100;

    private Thread exclusiveOwnerThread;

    private String parentLock = "lockkey";

    public RedisDistributedLock() {
    }

    /**
     * 自旋  阻塞锁
     */
    @Override
    public void lock() {
        while (!tryLock()) {
            try {
                logger.info("等待锁");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean tryLock() {
        return tryLock(time, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        String lockName = this.parentLock;
        String id = UUID.randomUUID().toString();
        Thread t = Thread.currentThread();
        //JedisPool jedisPool = RedisUtil.getJedisPool();
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            if("ok".equalsIgnoreCase(jedis.set(lockName,id,"NX","EX",unit.toMillis(time)))){
                logger.info("获取分布式锁成功");
                lockContext.set(id);
                setExclusiveOwnerThread(t);
                return true;
            }else if (exclusiveOwnerThread == t) { //当前线程已经获得了锁，可重入
                return true;
            }
            //加锁和有效期用一条命令完成  分成两条可能会产生死锁问题
            /*if (jedis.setnx(lockName, id) == 1) {  //加锁  ------  弃用
                jedis.pexpire(lockName, unit.toMillis(time));  //设置锁的有效期
                lockContext.set(id);
                setExclusiveOwnerThread(t);
                return true;
            } else if (exclusiveOwnerThread == t) { //当前线程已经获得了锁，可重入
                return true;
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
            /*if(jedis != null){
                jedisPool.returnResource(jedis);
            }
            if(jedisPool != null){
                jedisPool.close();
            }*/
        }

        return false;
    }

    @Override
    public void unlock() {
        String lockName = this.parentLock;
        String script = null;
        //JedisPool jedisPool = RedisUtil.getJedisPool();
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            //我们这里引入了lua脚本，主要的好处是Redis可以通过eval命令保证代码执行的原子性，脚本内容如下
            script = readInputStream(getClass().getClassLoader().getResourceAsStream("redis.scripts"));
            if (lockContext.get() == null) {
                return;
            }
            jedis.eval(script, Arrays.asList(lockName), Arrays.asList(lockContext.get()));
            logger.info("解锁成功");
            lockContext.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis != null){
                //jedisPool.returnResource(jedis);
                jedis.close();
            }
            /*if(jedisPool != null){
                jedisPool.close();
            }*/
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private String readInputStream(InputStream in) {
        try {
            StringBuffer buffer = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1; ) {
                buffer.append(new String(b, 0, n));
            }
            in.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }

    public String getParentLock() {
        return parentLock;
    }

    public void setParentLock(String parentLock) {
        this.parentLock = parentLock;
    }
}
