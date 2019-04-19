package com.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class ConnectionPool implements IConnectionPool {

    // 使用线程安全的集合 空闲线程 容器 没有被使用的连接存放
    private List<Connection> freeConnection = new Vector<>();
    // 使用线程安全的集合 活动线程 容器 容器正在使用的连接
    private List<Connection> activeConnection = new Vector<>();

    private DbBean dbBean;

    private int countConne = 0;

    public ConnectionPool(DbBean dbBean) {
        this.dbBean = dbBean;
        init();
    }

    private void init() {
        if (dbBean == null) {
            return;// 注意最好抛出异常
        }

        for (int i = 0; i < dbBean.getInitConnections(); i++) {
            Connection connection = newConnection();
            if (connection != null) {
                // 3.存放在freeConnection集合
                freeConnection.add(connection);
            }
        }
    }

    // 判断连接是否可用
    public boolean isAvailable(Connection connection) {
        try {
            if (connection == null || connection.isClosed()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return true;

    }

    // 创建Connection连接
    private synchronized Connection newConnection() {
        try{
            Class.forName(dbBean.getDriverName());
            Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUserName(),
                    dbBean.getPassword());
            return connection;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public synchronized Connection getConnection() {
        Connection connection = null;
        // 思考：怎么知道当前创建的连接>最大连接数
        try {
            if (countConne < dbBean.getMaxActiveConnections()) {
                // 小于最大活动连接数
                // 1.判断空闲线程是否有数据
                if (freeConnection.size() > 0) {
                    // 空闲线程有存在连接
                    // ==freeConnection.get(0);freeConnection.remove(0)
                    // 拿到在删除
                    connection = freeConnection.remove(0);
                } else {
                    // 创建新的连接
                    connection = newConnection();
                }

                // 判断连接是否可用
                boolean available = isAvailable(connection);
                if (available) {
                    // 存放在活动线程池
                    activeConnection.add(connection);
                    countConne++;
                } else {
                    countConne--;
                    connection = getConnection();// 怎么使用重试？ 递归算法
                }

            }else {
                // 大于最大活动连接数，进行等待
                wait(dbBean.getConnTimeOut());
                // 重试
                connection = getConnection();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public synchronized void releaseConnection(Connection connection) {
        try {
            if (isAvailable(connection)) {
                // 2.判断空闲线程是否已满
                if (freeConnection.size() < dbBean.getMaxConnections()) {
                    freeConnection.add(connection);
                }else {
                    connection.isClosed();
                }
                activeConnection.remove(connection);
                countConne--;
                notifyAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
