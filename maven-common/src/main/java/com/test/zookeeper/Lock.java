package com.test.zookeeper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public interface Lock{

    //获取锁
    void lock();

    void lockInterruptibly() throws InterruptedException;

    //非阻塞
    boolean tryLock();

    //规定时间内阻塞  半阻塞
    boolean tryLock(long var1, TimeUnit var3) throws InterruptedException;

    //解锁
    void unlock();

    Condition newCondition();

}
