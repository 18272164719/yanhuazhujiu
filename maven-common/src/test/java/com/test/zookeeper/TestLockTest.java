package com.test.zookeeper;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class TestLockTest {


    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private CountDownLatch ctld = new CountDownLatch(1);

    private int stock = 8;

    private Lock lock = new ZookeeperLock("/testZk");

    @Test
    public void test1() throws Exception {
        for( int i =0 ;i<= 9;i++){
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    add(1);
                }
            });

        }
        Thread.sleep(1000);
        ctld.countDown();
    }

    public void add (int num){
        boolean flag = false;
        try{
            lock.lock();
            if(stock - num >= 0){
                try {
                    ctld.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stock -= num;
                //stock++;
                flag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        if(flag)
            System.out.println(Thread.currentThread().getName()+"购票成功,剩余："+stock+"张");
        else
            System.err.println(Thread.currentThread().getName()+"购票失败，剩余"+stock+"张");
    }

}