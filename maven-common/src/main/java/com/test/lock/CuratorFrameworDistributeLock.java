package com.test.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class CuratorFrameworDistributeLock implements Lock {

    private final static Logger logger = LoggerFactory.getLogger(CuratorFrameworDistributeLock.class);

    private CuratorFramework client = null;		// zk客户端

    // 用于挂起当前请求，并且等待上一个分布式锁释放
    private static CountDownLatch zkLocklatch = new CountDownLatch(1);

    // 分布式锁的总节点名
    private static final String ROOT_LOCK = "/imooc-locks";

    private String lockName;

    public CuratorFrameworDistributeLock(String hostName,String lockName){
        this.lockName = lockName;
        client = CuratorFrameworkFactory.newClient(hostName,
                300, 100,
                new ExponentialBackoffRetry(1000, 3));
        client.start();
        try{
            if(client.checkExists().forPath(ROOT_LOCK) == null){
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(ROOT_LOCK);
            }
            // 针对zk的分布式锁节点，创建相应的watcher事件监听
            addWatcherToLock(ROOT_LOCK);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        while (!tryLock()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean tryLock() {
        try{
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(ROOT_LOCK + "/" + lockName);
            logger.info("获得分布式锁成功...");
            System.out.println("获得分布式锁成功...");
            return true;
        }catch (Exception e){
            logger.info("获取分布式锁失败");
            try{
                // 如果没有获取到锁，需要重新设置同步资源值
                if(zkLocklatch.getCount() <= 0){
                    zkLocklatch = new CountDownLatch(1);
                }
                // 阻塞线程
                zkLocklatch.await();
                return false;
            }catch (Exception pe){
                pe.printStackTrace();
            }

        }
        return false;
    }

    @Override
    public boolean tryLock(long var1, TimeUnit var3) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        try{
            if (client.checkExists().forPath(ROOT_LOCK + "/" + lockName) != null) {
                client.delete().forPath(ROOT_LOCK + "/" + lockName);
            }
            System.out.println("删除分布式锁成功...");
        }catch (Exception e){

        }
    }

    /**
     * @Description: 创建watcher监听
     */
    public void addWatcherToLock(String path) throws Exception {
        final PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    String path = event.getData().getPath();
                    logger.info("上一个会话已释放锁或该会话已断开, 节点路径为: " + path);
                    if(path.contains(lockName)) {
                        logger.info("释放计数器, 让当前请求来获得分布式锁...");
                        zkLocklatch.countDown();
                    }
                }
            }
        });
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
