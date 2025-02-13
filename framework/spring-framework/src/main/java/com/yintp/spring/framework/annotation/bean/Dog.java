package com.yintp.spring.framework.annotation.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {

    private String name;

    public Dog() {
        System.out.println("dog construct() ...");
    }

    /**
     * 对象创建并赋值之后调用
     */
    @PostConstruct
    public void init() {
        System.out.println("dog @PostConstruct ...");
    }

    /**
     * 容器移除bean之前调用
     */
    @PreDestroy
    public void destory() {
        System.out.println("dog @PreDestroy ...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }
}
