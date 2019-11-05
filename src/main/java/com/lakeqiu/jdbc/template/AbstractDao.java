package com.lakeqiu.jdbc.template;

import com.lakeqiu.jdbc.error.DaoException;
import com.lakeqiu.jdbc.utils.JdbcUtils;

import java.sql.*;

/**
 * @author lakeqiu
 */
public abstract class AbstractDao {
    /**
     * 增删改模板
     * @return
     */
    public int update(String sql, Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 逐个设置参数进sql语句中
            for (int i = 0; i < args.length; i++){
                ps.setObject(i+1, args[i]);
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }finally {
            JdbcUtils.close(rs, ps, conn);
        }
    }

    /**
     * 查询模板
     * @param sql sql语句
     * @param args 参数
     * @return
     */
    public Object query(String sql, Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 逐个设置参数进sql语句中
            for (int i = 0; i < args.length; i++){
                ps.setObject(i+1, args[i]);
            }

            // 查询等到结果集
            rs = ps.executeQuery();

            // 映射结果集
            return rowMapper(rs);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }finally {
            JdbcUtils.close(rs, ps, conn);
        }
    }

    /**
     * 映射结果集，让子类去实现
     * @param rs 结果集
     * @return
     */
    abstract protected Object rowMapper(ResultSet rs);
}
