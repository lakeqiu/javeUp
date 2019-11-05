package com.lakeqiu.jdbc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 模板方法
 * JDBC1.0版本
 * @author lakeqiu
 */
public class JdbcUtils {

    private static Properties prop;

    /**
     * 保证在JdbcUtils加载时只被加载一次
     */
    static {
        try {
            // 读取数据库配置信息
            InputStream resource = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            prop = new Properties();
            prop.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 加载数据库驱动
        try {
            Class.forName(prop.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *  获取数据库连接
     * @return connection
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(prop.getProperty("url"),
                prop.getProperty("username"),
                prop.getProperty("password"));
    }


    /**
     * 释放数据库连接
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st, Connection conn){
        try {
            if (rs != null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (st != null){
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    if (conn != null){
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
