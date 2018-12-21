package com.test.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Intercepts(value = {@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class EntityInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();


        Object obj = invocation.proceed();
        logger.info("obj--------------" + obj);
        return obj;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        if (properties.isEmpty()) {
            return;
        }
        Long id = Long.parseLong(properties.getProperty("id"));
        logger.info("id ---------------" + id);
        String createUser = properties.getProperty("createUser");
        logger.info("createUser ---------------" + createUser);
        String modifyUser = properties.getProperty("modifyUser");
        logger.info("modifyUser ---------------" + modifyUser);

        String dialect = properties.getProperty("dialect");
        logger.info("mybatis intercept dialect:{}", dialect);

    }
}
