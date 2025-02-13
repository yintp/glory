package com.yintp.spring.framework.annotation.bean;

import org.springframework.beans.factory.annotation.Value;

public class Man {

    /**
     * @Value赋值：
     * 1）、基本数值
     * 2）、SpEL表达式
     * 3）、取出外部配置的值${}
     */
    @Value("张三")
    private String name;
    @Value("#{20-2}")
    private Integer age;
    @Value("${man.nickName}")
    private String nickName;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Man{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
