package com.test.redis;

import java.util.List;

public interface IRedisDao {

    Object getNode(String key);

    Object get(String key);

    boolean setNode (String key ,Object value);

    boolean set (String key, Object value, Long expireTime);

    void remove (String... keys);

    void remove (String key);

    void removePattern (String pattern);

    boolean exists(String key);

    long increment(String key, long delta);
}
