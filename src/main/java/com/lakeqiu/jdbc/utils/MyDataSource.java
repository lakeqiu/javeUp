package com.lakeqiu.jdbc.utils;

import com.lakeqiu.jdbc.error.DaoException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * 数据源
 * @author lakeqiu
 */
public class MyDataSource {

    private static Properties props = null;

    static {
        try {
            // 获取数据库连接配置信息流
            InputStream inputStream = MyDataSource.class.getClassLoader().getResourceAsStream("jdbc.properties");
            props = new Properties();
            // 读取流
            props.load(inputStream);
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * 连接池初始连接数
     */
    private static int intCount = 5;

    /**
     * 连接池允许存在最小空闲连接数（连接池中存在连接数量），小于这个数就要创建新连接
     */
    private static int minIdleCount = 3;

    /**
     * 连接池允许存在最大连接数
     */
    private static int maxIdleCount = 20;

    /**
     * 当前连接池中存在连接数量
     */
    private static int currentIdleCount = 0;

    /**
     * 创建连接次数
     */
    private static int createCount = 0;

    /**
     * 连接池，存放连接容器
     */
    private final static List<Connection> CONNECTION_POOLS = new LinkedList<>();

    /**
     * 空参构造，按照initCount创建出初始连接数并放入连接池中
     */
    public MyDataSource(){
        try {
            for (int i = 0; i < intCount; i++){
                // 创建连接
                Connection connection = DriverManager.getConnection(props.getProperty("url"),
                        props.getProperty("user"),
                        props.getProperty("password"));
                // 创建连接代理对象并存入连接池中
                CONNECTION_POOLS.add(this.createProxyConnection(connection));
                currentIdleCount++;
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public Connection getConnection(){
        synchronized (CONNECTION_POOLS){
            // 连接池中还存在连接
            if (currentIdleCount > 0){
                currentIdleCount--;
            }else { // 连接池中已经没有连接了，重新创建
                // 创建连接
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(props.getProperty("url"),
                            props.getProperty("user"),
                            props.getProperty("password"));

                    // 创建连接代理对象并存入连接池中
                    CONNECTION_POOLS.add(this.createProxyConnection(connection));
                    currentIdleCount++;
                } catch (Exception e) {
                    throw new DaoException(e.getMessage());
                }
            }
            // 从连接池中取出第一个连接并移除
            return CONNECTION_POOLS.remove(1);
        }
    }

    /**
     * 私有方法，用于生成代理连接与拦截close方法，判断是将conn放回连接池还是真正释放
     * 调用时机：数据源初始化，以及用户调用dataSource.getConnection时
     * @param realConn 数据库连接
     * @return 连接代理对象
     */
    private Connection createProxyConnection(Connection realConn) {
        // 这句代码仅仅是为了把realConn转为final，这样才能在匿名对象invocationHandler中使用
        final Connection realConnection = realConn;

        Connection proxyConnection = (Connection) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                realConnection.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 拦截close方法
                        if ("close".equals(method.getName())) {
                            // 连接池池中存在连接数量小于最大数量，说明连接还可以放得下
                            if (minIdleCount < maxIdleCount) {
                                CONNECTION_POOLS.add((Connection) proxy);
                                currentIdleCount++;

                                // 表示释放成功
                                return 1;
                            } else {
                                // 连接池已经满了，连接只能销毁
                                realConnection.close();

                                // 表示释放成功
                                return 1;
                            }
                        }
                        return method.invoke(realConnection, args);
                    }
                });

        return proxyConnection;
    }
}
