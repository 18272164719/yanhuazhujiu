package com.test.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 */
public class TestExecutorService {

    private Integer ticket = 8;

    private static Integer num = 1;

    private static CountDownLatch ctl = new CountDownLatch(1);

    /**
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
     */
    private static ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     */
    private static ExecutorService fiexdThreadPool = Executors.newFixedThreadPool(10);
    /**
     * 创建一个定长线程池，支持定时及周期性任务执行
     */
    private static ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
    /**
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
     */
    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


    public static void main(String[] args) {
        try {
            final TestExecutorService testExecutorService = new TestExecutorService();
            for (int i = 0; i <= 9; i++) {
                cacheThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        testExecutorService.buy(num);
                    }
                });
            }
            Thread.sleep(1000);
            ctl.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buy(Integer num) {

        boolean flag = false;
        Lock lock = new CuratorFrameworDistributeLock("192.168.11.190:2181","pp");
        lock.lock();
        try{
            if ((ticket - num) >= 0) {
                try {
                    ctl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket -= num;
                flag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        if(flag)
            System.out.println(Thread.currentThread().getName() + "购买成功！,剩余票数为" + ticket + "张");
        else
            System.err.println(Thread.currentThread().getName() + "购买失败！,剩余票数为" + ticket + "张");
    }
}

