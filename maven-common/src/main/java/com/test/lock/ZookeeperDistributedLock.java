package com.test.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * ZookeeperLock 分布式锁  性能高  用 ZookeeperLock
 */
public class ZookeeperDistributedLock implements Watcher,Lock{

    private static Logger logger = LoggerFactory.getLogger(ZookeeperDistributedLock.class);

    private ZooKeeper zk = null;
    // 根节点
    private String ROOT_LOCK = "/locks";
    // 竞争的资源
    private String lockName;
    // 等待的前一个锁
    private String WAIT_LOCK;
    // 当前锁
    private String CURRENT_LOCK;

    private ThreadLocal<String> context = new ThreadLocal<>();

    // 用于挂起当前请求，并且等待上一个分布式锁释放
    private static CountDownLatch zkLocklatch;

    private int sessionTimeout = 30000;

    private List<Exception> exceptionList = new ArrayList<Exception>();

    public ZookeeperDistributedLock(String hostName,String lockName){
        this.lockName = lockName;
        try {
            zk = new ZooKeeper(hostName,sessionTimeout,this);
            Stat stat = zk.exists(ROOT_LOCK, false);
            if(stat == null){
                //如果根节点不存在，则创建根节点
                zk.create(ROOT_LOCK,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取到锁返回，阻塞
     */
    @Override
    public void lock() {
        try{
            if(tryLock()){
                System.out.println(Thread.currentThread().getName() + " " + lockName + "获得了锁");
                return;
            }else{
                //等待锁
                System.out.println("等待锁");
                waitForLock(WAIT_LOCK, sessionTimeout);
                lock();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock() {
        try{
            String splitStr = "_lock_";
            if (lockName.contains(splitStr)) {
                throw new LockException("锁名有误");
            }
            // 创建临时有序节点
            CURRENT_LOCK = zk.create(ROOT_LOCK+"/" + lockName + splitStr, new byte[0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            context.set(CURRENT_LOCK);
            //取出所有节点
            List<String> subNodes = zk.getChildren(ROOT_LOCK,false);
            // 取出所有lockName的锁
            List<String> lockObjects = new ArrayList<String>();
            for (String node : subNodes) {
                String _node = node.split(splitStr)[0];
                if (_node.equals(lockName)) {
                    lockObjects.add(node);
                }
            }
            Collections.sort(lockObjects);
            System.out.println(Thread.currentThread().getName() + " 的锁是 " + CURRENT_LOCK);
            // 若当前节点为最小节点，则获取锁成功
            if (CURRENT_LOCK.equals(ROOT_LOCK + "/" + lockObjects.get(0))) {
                return true;
            }
            // 若不是最小节点，则找到自己的前一个节点
            String prevNode = CURRENT_LOCK.substring(CURRENT_LOCK.lastIndexOf("/") + 1);
            WAIT_LOCK = lockObjects.get(Collections.binarySearch(lockObjects, prevNode) - 1);

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit var3) {
        try{
            if(tryLock()){
                return true;
            }
            return waitForLock(WAIT_LOCK,timeout);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void unlock() {
        try {
            System.out.println("释放锁 " + CURRENT_LOCK);
            zk.delete(CURRENT_LOCK,-1);
            CURRENT_LOCK = null;
            zk.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean waitForLock (String prevNode,long waitTime)throws KeeperException, InterruptedException{

        Stat stat = zk.exists(ROOT_LOCK+"/"+prevNode,true);
        if(stat != null){
            this.zkLocklatch = new CountDownLatch(1);
            // 计数等待，若等到前一个节点消失，则precess中进行countDown，停止等待，获取锁
            this.zkLocklatch.await(waitTime, TimeUnit.MILLISECONDS);
            this.zkLocklatch = null;
            System.out.println("等到了锁");
        }
        return true;
    }

    /**
     * 节点监视器
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        if (this.zkLocklatch != null) {
            this.zkLocklatch.countDown();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.lock();
    }

    public class LockException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public LockException(String e){
            super(e);
        }
        public LockException(Exception e){
            super(e);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }
}

