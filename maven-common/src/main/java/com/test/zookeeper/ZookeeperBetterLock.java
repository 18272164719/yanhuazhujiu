package com.test.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.zookeeper.ZooKeeper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class ZookeeperBetterLock extends AbstractLock {

    private ZkClient zk = null;

    private final static String ROOT_LOCK = "/nice";

    private String lockName;

    private CountDownLatch ctl;

    private String currentLock;

    private String beforeLock;

    private int count = 0;

    public ZookeeperBetterLock(String lockName) {
        this.lockName = lockName;
        this.zk = new ZkClient("192.168.11.190:2181", 100000);
        if (!zk.exists(ROOT_LOCK)) {
            zk.createPersistent(ROOT_LOCK,Thread.currentThread().getName());
        }
    }

    @Override
    public void lock() {
        count++;
        //System.out.println("回掉函数执行了"+ count+"次");
        if (tryLock()) {
            //return;
        } else {
            waitForLock();
            getLock();
        }
    }

    @Override
    public boolean tryLock() {
        System.out.println("当前锁为"+currentLock);
        if(currentLock == null){
            //创建临时有序节点
            currentLock = zk.createEphemeralSequential(ROOT_LOCK + lockName, Thread.currentThread().getName());
            List<String> nodeList = zk.getChildren(ROOT_LOCK);
            Collections.sort(nodeList);
            if(currentLock.equals(ROOT_LOCK+"/"+nodeList.get(0))){
                System.out.println(Thread.currentThread().getName()+"抢到了锁,当前结点为"+currentLock);
                return true;
            }else{
                String prevNode = currentLock.substring(currentLock.lastIndexOf("/") + 1);
                beforeLock = ROOT_LOCK+"/"+nodeList.get(Collections.binarySearch(nodeList, prevNode) - 1);
                return false;
            }
        }else{
            return true;
        }
    }

    @Override
    public void waitForLock() {
        //监听事件
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
                System.out.println(ctl+"开始计时");
                if(ctl != null){
                    ctl.countDown();
                }
            }
        };
        /**
         * 给beforeLock锁增加监听事件
         */
        zk.subscribeDataChanges(beforeLock,listener);
        if(zk.exists(beforeLock)){
            ctl = new CountDownLatch(1);
            try {
                System.out.println("开始等待"+beforeLock+"节点删除");
                ctl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("移除监听");
            zk.unsubscribeDataChanges(beforeLock,listener);
        }
    }

    @Override
    public void unlock() {
        if(zk != null){
            System.out.println(Thread.currentThread().getName() + "释放锁");
            zk.delete(currentLock);
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
