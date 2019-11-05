package com.lakeqiu.clone.deepclone;

import org.junit.Test;

/**
 * @author lakeqiu
 */
public class CloneTest {

    @Test
    public void test() throws CloneNotSupportedException {
        Person person = new Person("jar", 1000000000, "男", new Car("car"));
        Person clone = (Person) person.clone();
        // TODO
        person.setAge(9999999);
        System.out.println("Person--->" + person.getAge().hashCode());
        System.out.println("Clone--->" + clone.getAge().hashCode());
        System.out.println((person.getAge() == clone.getAge()));
    }
}
