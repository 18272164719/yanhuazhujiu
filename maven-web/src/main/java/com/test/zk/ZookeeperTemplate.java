package com.test.zk;

public abstract class ZookeeperTemplate implements IClientTemplate{

    public void createNode(String path, Object object) {
    }

    public void updateNode(String path, Object object) {
    }

    public void deleteNode(String path) {
    }

    public Object getNode(String path) {
        return null;
    }

    protected abstract void createParentNode(String var1);
}
