package com.test.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * 基于zookeeper的分布式锁  优雅的实现阻塞锁，不是重复的去尝试锁
 * 创建临时节点
 */
public class ZookeeperLock extends AbstractLock implements Lock{

    private ZkClient zk = null;

    private String lockName ;

    private CountDownLatch ctl ;

    private int count = 0;

    public ZookeeperLock(String lockName){
        this.lockName = lockName;
        this.zk = new ZkClient("192.168.11.200:2181",10000);
    }

    @Override
    public void lock() {
        count++;
        //System.out.println("回掉函数执行了"+ count+"次");
        if(tryLock()){
            //System.out.println(Thread.currentThread().getName()+"抢占得了锁");
        }else {
            waitForLock();
            //System.out.println(Thread.currentThread().getName()+"重新尝试获取锁");
            getLock();
        }
    }

    @Override
    public boolean tryLock() {
        try{
            zk.createEphemeral(lockName);
            System.out.println(Thread.currentThread().getName() + "拿到了锁");
        }catch (Exception e){
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public void waitForLock() {
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
            }
            /**
             * 监测节点的删除事件
             * @param dataPath
             * @throws Exception
             */
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                //System.out.println(ctl+"开始计时");
                if(ctl != null){
                    ctl.countDown();
                }

            }
        };
        /**
         * 给该锁增加监听事件，这里可能会发生羊群反应
         */
        zk.subscribeDataChanges(lockName,listener);
        //System.out.println("进入监听程序");
        if(zk.exists(lockName)){
            ctl = new CountDownLatch(1);
            try {
                //System.out.println("开始等待节点删除");
                ctl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }/*else{
            lock();
        }*/
        //zk.unsubscribeDataChanges(lockName,listener);
    }

    @Override
    public void unlock() {
        if(zk != null){
            System.out.println(Thread.currentThread().getName() + "释放锁");
            zk.delete(lockName);
        }
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long var1, TimeUnit var3) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }
}
