package com.test.Shiro;

import com.test.util.RedisUtil;
import com.test.util.EhcacheUtil;
import com.test.zk.ZookeeperTemplate;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class ShiroSessionDAO extends EnterpriseCacheSessionDAO {

    private ZookeeperTemplate zookeeperTemplate;
    private String parentPath = "/SISSION";
    private RedisUtil redisUtil;
    private EhcacheUtil ehcacheUtil;

    private static Logger logger = LoggerFactory.getLogger(ShiroSessionDAO.class);

    public void doUpdate(Session session) {
        logger.info(session == null ? "null" : session.getId().toString());
        if (session == null || session.getId() == null) {
            System.err.println("session argument cannot be null.");
        }

        String path = this.parentPath + '/' + session.getId().toString();
        logger.info("存的时候的路径-------"+path);
        if (this.zookeeperTemplate != null) {
            this.zookeeperTemplate.updateNode(path, session);
        }

        if (this.redisUtil != null) {
            this.redisUtil.set(path, session);
        }

        if (this.ehcacheUtil != null) {
            this.ehcacheUtil.put(this.parentPath, session.getId().toString(), session);
        }
    }

    public void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            System.err.println("session argument cannot be null.");
        }
        logger.info("------进入了deletesession方法");
        String path = this.parentPath + '/' + session.getId().toString();
        if (this.zookeeperTemplate != null) {
            this.zookeeperTemplate.deleteNode(path);
        }

        if (this.redisUtil != null) {
            this.redisUtil.remove(path);
        }

        if (this.ehcacheUtil != null) {
            this.ehcacheUtil.remove(this.parentPath, session.getId().toString());
        }


    }

    public Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        String path = this.parentPath + '/' + session.getId().toString();

        if(this.zookeeperTemplate != null){
            Object obj = zookeeperTemplate.getNode(path);
            if(obj == null){
                synchronized (ShiroSessionDAO.class){
                    obj = zookeeperTemplate.getNode(path);
                    if(obj == null){
                        this.zookeeperTemplate.createNode(path, session);
                    }
                }
            }
        }

        if (this.redisUtil != null) {
            this.redisUtil.set(path, session);
        }

        if (this.ehcacheUtil != null) {
            this.ehcacheUtil.put(this.parentPath, session.getId().toString(), session);
        }

        return sessionId;
    }

    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        Session session = this.getCachedSession(sessionId);
        if (session == null || session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
            session = this.doReadSession(sessionId);
            if (session == null) {
                throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
            }

            this.cache(session, session.getId());
        }

        return session;
    }

    public Session doReadSession(Serializable id) {
        if (id == null) {
            return null;
        } else {
            logger.info("session id = " + id);
            String path = this.parentPath + '/' + id.toString();
            logger.info("取的时候的路径-------"+path);
            Object object;

            if (this.zookeeperTemplate != null) {
                object = this.zookeeperTemplate.getNode(path);
                logger.info("object的数据--------------------"+object);
                if (object != null) {
                    return (Session)object;
                }
            }

            if (this.redisUtil != null) {
                object = this.redisUtil.get(path);
                if (object != null) {
                    return (Session)object;
                }
            }

            if (this.ehcacheUtil != null) {
                object = this.ehcacheUtil.get(this.parentPath, id.toString());
                if (object != null) {
                    return (Session)object;
                }
            }

            return null;
        }
    }

    public ZookeeperTemplate getZookeeperTemplate() {
        return zookeeperTemplate;
    }

    public void setZookeeperTemplate(ZookeeperTemplate zookeeperTemplate) {
        this.zookeeperTemplate = zookeeperTemplate;
    }

    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public EhcacheUtil getEhcacheUtil() {
        return ehcacheUtil;
    }

    public void setEhcacheUtil(EhcacheUtil ehcacheUtil) {
        this.ehcacheUtil = ehcacheUtil;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }
}
