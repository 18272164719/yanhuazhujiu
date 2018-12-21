package com.test.lock;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.*;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.listen.Listenable;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class CuratorDistributeLock implements Lock{

    private InterProcessMutex lock = null;

    public CuratorDistributeLock(String hostName,String lockName){
        CuratorFramework client = CuratorFrameworkFactory.newClient(hostName,
                300, 100,
                new ExponentialBackoffRetry(1000, 3));
        client.start();
        this.lock = new InterProcessMutex(client, lockName);
    }

    @Override
    public void lock() {
        try {
            this.lock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long var1, TimeUnit var3) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        try {
            this.lock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
