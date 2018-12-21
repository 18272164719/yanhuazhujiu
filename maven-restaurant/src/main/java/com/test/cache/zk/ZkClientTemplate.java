package com.test.cache.zk;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ZkClientTemplate extends ZookeeperTemplate{

    private ZkClient zkclient;

    public ZkClient getZkclient() {
        return zkclient;
    }

    public void setZkclient(ZkClient zkclient) {
        this.zkclient = zkclient;
    }

    @Override
    protected void createParentNode(String parentPath) {
        if(!this.zkclient.exists(parentPath)){
            this.zkclient.create(parentPath,"", CreateMode.PERSISTENT);
        }
    }



}
