package com.test.lock;

import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;

/**
 * 对于单机和分布式系统  用来测试分布式锁
 */
public class TestBuyTicket {

    private Lock lock1 = new RedisDistributedLock();

    private Lock lock2 = new RedisTemplateDistributedLock(RedisUtil.getRedisTemplate(),"key");
    /**
     * 计数器   用于多个线程等待主线程  模拟高并发
     */
    private static CountDownLatch ctl = new CountDownLatch(1);

    Integer stock = 8;

    public void buy(int num) {
        boolean flag = false;
        synchronized (this) {
            if ((stock - num) >= 0) {
                try {
                    ctl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stock -= num;
                flag = true;
            }
        }
        if (flag)
            System.out.println(Thread.currentThread().getName() + "交易成功：卖出" + String.valueOf(num) + "张票，库存剩余" + stock + "票");
        else
            System.err.println(Thread.currentThread().getName() + "交易事失败：库存不足" + String.valueOf(num) + "张票，库存剩余" + stock + "票");
    }

    public void buyUseLock(int num) {
        boolean flag = false;
        try {
            lock1.lock();
            if ((stock - num) >= 0) {
                try {
                    ctl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stock -= num;
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock1.unlock();
        }
        if (flag)
            System.out.println(Thread.currentThread().getName() + "交易成功：卖出" + String.valueOf(num) + "张票，库存剩余" + stock + "票");
        else
            System.err.println(Thread.currentThread().getName() + "交易事失败：库存不足" + String.valueOf(num) + "张票，库存剩余" + stock + "票");
    }

    public void buyUseLockTemp(int num) {
        boolean flag = false;
        try {
            lock2.lock();
            if ((stock - num) >= 0) {
                try {
                    ctl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stock -= num;
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock2.unlock();
        }
        if (flag)
            System.out.println(Thread.currentThread().getName() + "交易成功：卖出" + String.valueOf(num) + "张票，库存剩余" + stock + "票");
        else
            System.err.println(Thread.currentThread().getName() + "交易事失败：库存不足" + String.valueOf(num) + "张票，库存剩余" + stock + "票");
    }

    public static void main(String[] args) throws InterruptedException {
        final TestBuyTicket testBuyTicket = new TestBuyTicket();
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testBuyTicket.buyUseLockTemp(1);
                }
            }, "用户" + i).start();
        }
        Thread.sleep(100);
        ctl.countDown();
    }

}
