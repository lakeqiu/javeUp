package com.lakeqiu.jdbc.dao;

import com.lakeqiu.jdbc.pojo.Person;

import java.util.List;

/**
 * @author lakeqiu
 */
public interface PersonDao {
    /**
     * 更新
     * @param person
     */
    int update(Person person);

    /**
     * 删除
     * @param person
     */
    int delete(Person person);

    /**
     * 插入
     * @param person
     */
    int insert(Person person);

    /**
     * 查询
     * @param person
     * @return
     */
    List query(Person person);
}
