package com.lakeqiu.jdbc.row;

import java.sql.ResultSet;

/**
 * @author lakeqiu
 */
public interface RowMapper {
    /**
     * 映射结果集
     * @param rs
     * @return
     */
    Object mapRow(ResultSet rs);
}
