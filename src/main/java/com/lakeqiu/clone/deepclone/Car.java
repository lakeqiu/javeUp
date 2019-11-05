package com.lakeqiu.clone.deepclone;

import java.io.Serializable;

/**
 * @author lakeqiu
 */
public class Car implements Serializable {
    private static final long serialVersionUID = 1973404414081530138L;
    private String name;

    public Car() {
    }

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
