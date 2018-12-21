package com.test.cache.dao;

import com.test.cache.zk.ZookeeperTemplate;

public class TaskLockDAO {

    private String parentPath = "/com.test";
    private ZookeeperTemplate zookeeperTemplate;

    public Boolean lock (String lockPath){
        String path = this.parentPath + '/' + lockPath;

        try {
            if(this.zookeeperTemplate != null){
                Object object = this.zookeeperTemplate.getNode(path);
                if (object == null) {
                    this.zookeeperTemplate.createNode(path, "");
                    return true;
                }
            }
        }catch (Exception var4){
            var4.printStackTrace();
        }
        return false;
    }

    public void unlock (String lockPath){
        String path = this.parentPath + '/' + lockPath;
        if (this.zookeeperTemplate != null) {
            this.zookeeperTemplate.deleteNode(path);
        }
    }


    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public ZookeeperTemplate getZookeeperTemplate() {
        return zookeeperTemplate;
    }

    public void setZookeeperTemplate(ZookeeperTemplate zookeeperTemplate) {
        this.zookeeperTemplate = zookeeperTemplate;
    }
}
