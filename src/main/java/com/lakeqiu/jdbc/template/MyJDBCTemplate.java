package com.lakeqiu.jdbc.template;

import com.lakeqiu.jdbc.error.DaoException;
import com.lakeqiu.jdbc.row.RowMapper;
import com.lakeqiu.jdbc.utils.JdbcUtils;
import com.lakeqiu.jdbc.utils.JdbcUtils2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板模式 + 策略模式
 * @author lakeqiu
 */
public class MyJDBCTemplate {
    /**
     * 增删改模板
     * @return
     */
    public int update(String sql, Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = JdbcUtils2.getConnection();
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
    public List<Object> query(String sql, RowMapper rowMapper, Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = JdbcUtils2.getConnection();
            ps = conn.prepareStatement(sql);
            // 逐个设置参数进sql语句中
            for (int i = 0; i < args.length; i++){
                ps.setObject(i+1, args[i]);
            }

            // 查询等到结果集
            rs = ps.executeQuery();

            // 存放结果
            List<Object> list = new ArrayList<>();

            // 映射结果集
            while (rs.next()){
                Object o = rowMapper.mapRow(rs);
                list.add(o);
            }

            return list;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }finally {
            JdbcUtils.close(rs, ps, conn);
        }
    }
}