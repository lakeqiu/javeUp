package com.lakeqiu.classloader;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author lakeqiu
 */
public class ThreadClassLoader {
    public static void main(String[] args) {

        // 里面会去先获取线程上下文加载器然后用其去加载
        ServiceLoader<Driver> drivers = ServiceLoader.load(Driver.class);

        Iterator<Driver> iterator = drivers.iterator();

        while (iterator.hasNext()){
            Driver driver = iterator.next();

            System.out.println("driver:" + driver.getClass() + ", classloader:" + driver.getClass().getClassLoader());
        }

        System.out.println("当前线程上下文加载器:" + Thread.currentThread().getContextClassLoader());
        System.out.println("ServiceLoader的类加载器" + ServiceLoader.class.getClassLoader());

//        Connection connection = DriverManager.getConnection("url", "user", "password");
    }
}
