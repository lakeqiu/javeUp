package com.lakeqiu.jdbc;

import com.lakeqiu.jdbc.dao.PersonDao;
import com.lakeqiu.jdbc.dao.impl.PersonDaoimpl;
import com.lakeqiu.jdbc.dao.impl.PersonDaoimpl2;
import com.lakeqiu.jdbc.pojo.Person;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author lakeqiu
 */
public class JDBCTest {

    @Test
    public void testDriver() throws SQLException {
        //1. 创建一个 Driver 实现类的对象
        Driver driver = new com.mysql.jdbc.Driver();

        //2. 准备连接数据库的基本信息: url, user, password
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "123456");

        //3. 调用 Driver 接口的　connect(url, info) 获取数据库连接
        Connection connection = driver.connect(url, info);
        System.out.println(connection);
    }


    @Test
    public void testDriverManager() throws Exception{
        //1. 驱动的全类名
        String driverClass = "com.mysql.jdbc.Driver";
        //2. 准备连接数据库的基本信息: url, user, password
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String password = "123456";

        //2. 加载数据库驱动程序(对应的 Driver 实现类中有注册驱动的静态代码块)
        // 这里会初始化
        // 其中加载的类加载器是应用类加载器，其会获取类加载器存起来
        Class.forName(driverClass);

        //3. 通过 DriverManager 的 getConnection() 方法获取数据库连接
        // 因为加载的DriverManager是启动类加载器，所以正常情况下其是见不到mysql写的Driver实现类的，
        // 故会先去获取器forName方法阶段所存的类加器，如果不存在，就会去获取线程上下文类加载器
        Connection connection =
                DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }

    @Test
    public void testDao(){
        PersonDao dao = new PersonDaoimpl2();
        Person person = new Person("lake", 17);
        person.setId(2);
        dao.delete(person);
        /*dao.insert(new Person("qiu", 18));
        dao.insert(new Person("lakeqiu", 19));*/
        List list = dao.query(new Person(1));
        System.out.println(list);
    }
}
