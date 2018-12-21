package com.test.zk;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.List;

public class ZkClientTemplate extends ZookeeperTemplate{
    private ZkClient zkclient;

    public ZkClient getZkclient() {
        return this.zkclient;
    }

    public void setZkclient(ZkClient zkclient) {
        this.zkclient = zkclient;
    }

    public void createParentNode(String parentPath) {
        if (!this.zkclient.exists(parentPath)) {
            this.zkclient.create(parentPath, "", CreateMode.PERSISTENT);
            System.out.println("createParentNode:" + parentPath);
        }

    }

    public void createNode(String path, Object data) {
        if (!this.zkclient.exists(path)) {
            this.zkclient.create(path, data, CreateMode.EPHEMERAL);
            System.out.println("createNode:" + path);
        }

    }

    public void updateNode(String path, Object data) {
        if (this.zkclient.exists(path)) {
            this.zkclient.writeData(path, data);
            System.out.println("setData:" + path);
        }

    }

    public void deleteNode(String path) {
        if (this.zkclient.exists(path)) {
            this.zkclient.delete(path);
            System.out.println("deleteNode:" + path);
        }

    }

    public Object getNode(String path) {
        return this.zkclient.readData(path, true);
    }

    public List<String> getChildren(String path) {
        return this.zkclient.getChildren(path);
    }
}
