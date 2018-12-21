package com.test.cache.zk;

public abstract class ZookeeperTemplate {

    //创建节点
    public void createNode(String path ,Object object){
    }

    //更新节点
    public void updateNode(String path,Object object){
    }

    //删除节点
    public void deleteNode(String path){
    }

    public Object getNode(String path) {
        return null;
    }

    protected abstract void createParentNode(String var1);
}
