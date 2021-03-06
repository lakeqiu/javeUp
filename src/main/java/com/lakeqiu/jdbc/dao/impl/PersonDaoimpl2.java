package com.lakeqiu.jdbc.dao.impl;

import com.lakeqiu.jdbc.dao.PersonDao;
import com.lakeqiu.jdbc.error.DaoException;
import com.lakeqiu.jdbc.pojo.Person;
import com.lakeqiu.jdbc.row.RowMapper;
import com.lakeqiu.jdbc.template.MyJDBCTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lakeqiu
 */
public class PersonDaoimpl2 implements PersonDao, RowMapper {

    MyJDBCTemplate template = new MyJDBCTemplate();

    @Override
    public int update(Person person) {
        String sql =  "update person set name = ?, age = ? where id = ?";
        Object[] args = {person.getName(), person.getAge(), person.getId()};
        return template.update(sql, args);
    }

    @Override
    public int delete(Person person) {
        String sql = "delete from person where id = ?";
        return template.update(sql, person.getId());
    }

    @Override
    public int insert(Person person) {
        String sql = "insert into person(name, age) values(?, ?)";
        Object[] args = {person.getName(), person.getAge()};
        return template.update(sql, args);
    }

    @Override
    public List query(Person person) {
        String sql = "select * from person where id = ?";
        return (List) template.query(sql,this::mapRow, person.getId());
    }

    @Override
    public Object mapRow(ResultSet rs) {
        Person person = null;
        try {
            person = new Person();
            person.setId(rs.getInt("id"));
            person.setName(rs.getString("name"));
            person.setAge(rs.getInt("age"));
        } catch (SQLException e) {
            throw new DaoException("mapper Error!");
        }
        return person;
    }
}
