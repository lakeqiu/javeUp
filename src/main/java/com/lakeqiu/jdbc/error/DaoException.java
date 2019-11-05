package com.lakeqiu.jdbc.error;

/**
 * 为了不让service层直接处理异常与dao层总是catch异常造成代码繁琐，在dao层时抛出异常
 * SQLException是编译时异常，Service在调用DAO时必须处理异常，否则编译不通过。如何处理？
 * 要么继续抛，交给Controller处理（意义不大），要么try catch（Service层代码很臃肿，不美观）。
 * DAO接口有声明异常SQLException，这等于向外界暴露DAO层是JDBC实现。而且针对该接口只能用关系型数据库，耦合度太高了。后期无法切换DAO实现。
 * 比较好的做法是，将SQLException转为运行时异常抛出，Service层可处理也可不处理。
 * @author lakeqiu
 */
public class DaoException extends RuntimeException {

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}

