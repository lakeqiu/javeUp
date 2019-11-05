package com.lakeqiu.jdbc.factory;

/**
 * Dao层Bean工厂
 * @author lakeqiu
 */
public class DaoFactory {
    public static Object getBean(String name){
        try {
            // 获取字节码对象
            Class<?> aClass = Class.forName(name);
            // 创建反射对象
            Object o = aClass.newInstance();
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
