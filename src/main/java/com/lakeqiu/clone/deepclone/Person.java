package com.lakeqiu.clone.deepclone;

import java.io.*;

/**
 * @author lakeqiu
 */
public class Person implements Cloneable, Serializable{

    private static final long serialVersionUID = -3854378881006461705L;

    private String name;
    private Integer age;
    private String sex;

    private Car car;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Person(String name, Integer age, String sex, Car car) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.car = car;
    }

    /**
     * 因为
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Person() {
    }

    public Person(String name, Integer age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Person deepClone(){
        ByteArrayOutputStream outputStream = null;
        ObjectOutputStream os = null;
        ByteArrayInputStream inputStream = null;
        ObjectInputStream is = null;


        try {
            outputStream = new ByteArrayOutputStream();
            os = new ObjectOutputStream(outputStream);

            // 将对象以对象流方式输出
            os.writeObject(this);
            os.flush();

            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            is = new ObjectInputStream(inputStream);

            return (Person) is.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                // 先关内部的
                outputStream.close();
                os.close();
                inputStream.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
