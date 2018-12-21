package com.test.zk;

import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.ACLBackgroundPathAndBytesable;
import org.apache.curator.framework.api.BackgroundPathAndBytesable;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CuratorTemplate extends ZookeeperTemplate{

    private ZkSerializer zkSerializer;
    private CuratorFramework curatorFramework;
    private static Logger logger = LoggerFactory.getLogger(CuratorTemplate.class);

    public CuratorTemplate(org.apache.curator.framework.CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public void createParentNode(String parentPath) {
        try {
            Stat stat = (Stat)this.curatorFramework.checkExists().forPath(parentPath);
            if (stat == null) {
                ((BackgroundPathAndBytesable)((ACLBackgroundPathAndBytesable)this.curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)).forPath(parentPath, this.zkSerializer.serialize(""));
                System.out.println("createParentNode:" + parentPath);
            }

        } catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException("zookeeper根节点新增失败");
        }
    }

    public void createNode(String path, Object object) {
        try {
            ((BackgroundPathAndBytesable)((ACLBackgroundPathAndBytesable)this.curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)).forPath(path, this.zkSerializer.serialize(object));
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException("zookeeper节点新增失败");
        }

        logger.info("ZookeeperLock.createNode:" + path);
    }

    public void updateNode(String path, Object object) {
        try {
            Stat stat = (Stat)this.curatorFramework.checkExists().forPath(path);
            if (stat != null) {
                this.curatorFramework.setData().forPath(path, this.zkSerializer.serialize(object));
                System.out.println("setData:" + path);
            }

        } catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException("zookeeper节点修改失败");
        }
    }

    public void deleteNode(String path) {
        try {
            Stat stat = this.curatorFramework.checkExists().forPath(path);
            if(stat != null){
                this.curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException("zookeeper节点删除失败");
        }

        logger.info("ZookeeperLock.deleteNode:" + path);
    }

    public Object getNode(String path) {
        logger.info("ZookeeperLock.getNode:" + path);
        Object var2 = null;

        try {
            Stat stat = (Stat)this.curatorFramework.checkExists().forPath(path);
            if (stat != null) {
                byte[] data = (byte[])this.curatorFramework.getData().forPath(path);
                if (data != null) {
                    return this.zkSerializer.deserialize(data);
                }
            }

            return null;
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException("zookeeper节点查询失败");
        }
    }

    public List<String> getChildren(String path) {
        try {
            return (List)this.curatorFramework.getChildren().forPath(path);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public ZkSerializer getZkSerializer() {
        return this.zkSerializer;
    }

    public void setZkSerializer(ZkSerializer zkSerializer) {
        this.zkSerializer = zkSerializer;
    }
}
