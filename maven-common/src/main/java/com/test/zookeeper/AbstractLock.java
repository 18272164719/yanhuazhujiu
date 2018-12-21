package com.test.zookeeper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * 定义一个抽象的类
 */
public abstract class AbstractLock implements Lock{


    public void getLock() {
        System.out.println("重新尝试获取锁");
        if(tryLock()){
            //System.out.println(Thread.currentThread().getName()+"获得了锁");
        }else {
            waitForLock();
            getLock();
        }
    }


    public abstract boolean tryLock();


    public abstract void waitForLock();

}
