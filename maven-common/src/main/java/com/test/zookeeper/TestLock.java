package com.test.zookeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试zookeeper 分布式锁
 */
public class TestLock {

    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private static CountDownLatch ctld = new CountDownLatch(1);

    private int stock = 8;


    public static void main(String[] args) throws Exception{
        final TestLock testLock = new TestLock();
        for( int i =0 ;i<= 29;i++){
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    testLock.reduce(1);
                }
            });
        }
        Thread.sleep(1000);
        ctld.countDown();
    }

    public void reduce (int num){
        boolean flag = false;
        Lock lock = new ZookeeperBetterLock("/tomeet");
        //Lock lock = new ZookeeperLock("/testZk");

        try{
            lock.lock();
            if(stock - num >=0){
                try {
                    ctld.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                stock -= num;
                flag = true;
            }else{

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
