package com.yintp.jndi.api;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * @author yintp
 */
public class Person implements Remote, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
            "name='" + name + '\'' +
            '}';
    }
}
