package com.test.jdk;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过锁的粒度  来控制
 */
public class Jdk {

    private static ConcurrentHashMap<String, Lock> locks = new ConcurrentHashMap<>();


    /**
     *
     * @param key
     */
    public void doLock(String key){
        ReentrantLock newLock = new ReentrantLock();
        Lock oldLock = locks.putIfAbsent(key,newLock);
        if(oldLock == null){
           newLock.lock();
        }else{
            oldLock.lock();
        }
    }

    public void doLock2 (String key){
        ReentrantLock oldLock = (ReentrantLock) locks.get(key);
        if(oldLock == null){
            ReentrantLock newLock = (ReentrantLock)locks.putIfAbsent(key,new ReentrantLock());
            newLock.lock();
        }else{
            oldLock.lock();
        }
    }

    public void releaseLock (String key){
        ReentrantLock oldLock = (ReentrantLock) locks.get(key);
        if(oldLock != null && oldLock.isHeldByCurrentThread()){
            oldLock.unlock();
        }
    }
}
