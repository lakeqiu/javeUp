package com.lakeqiu.jdbc.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC2.0版本
 * @author lakeqiu
 */
public class JdbcUtils2 {
    /**
     * 初始化一个数据源
     */
    private static MyDataSource dataSource = new MyDataSource();

    /**
     * 获取连接
     */
    public static Connection getConnection(){
        // 从数据源获取Connection并返回
        return dataSource.getConnection();
    }

    /**
     * 获取数据源
     * @return
     */
    public static MyDataSource getDataSource() {
        return dataSource;
    }

    /**
     * 释放连接
     * @param rs
     * @param st
     * @param conn
     */
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
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
