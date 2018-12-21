package com.test.executor;

import com.test.config.Configuration;
import com.test.config.MappedStatement;
import com.test.reflection.ReflectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 不管任何框架，都要遵循jdbc规范
 */
public class DefaultExecutor implements Executor{

    private Configuration conf;

    public DefaultExecutor(Configuration conf) {
        this.conf = conf;
    }

    /**
     * 调用jdbc底层
     * @param ms
     * @param parameter
     * @param <E>
     * @return
     */
    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        List<E> resultList = new ArrayList<>();

        try {
            Class.forName(conf.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection(conf.getJdbcUrl(),conf.getJdbcUsername(),conf.getJdbcPassword());
            pre = conn.prepareStatement(ms.getSql());
            //处理sql的占位符
            parameterize(pre,parameter);

            resultSet = pre.executeQuery();

            handlerResultSet(resultSet,resultList,ms.getResultType());

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
                pre.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    private void parameterize(PreparedStatement pre, Object parameter) throws SQLException {
        if(parameter instanceof Integer){
            pre.setInt(1,(int)parameter);
        }else if(parameter instanceof Long){
            pre.setLong(1,(long)parameter);
        }else if(parameter instanceof String){
            pre.setString(1,(String)parameter);
        }

    }

    private <E> void handlerResultSet(ResultSet resultSet, List<E> resultList, String className) {
        Class<E> clazz = null;
        try {
            clazz = (Class<E>)Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                Object entity = clazz.newInstance();

                ReflectionUtil.setPropToEntityFromResultSet(resultSet,entity);
                resultList.add((E)entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
