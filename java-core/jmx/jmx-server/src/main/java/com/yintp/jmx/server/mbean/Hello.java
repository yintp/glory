package com.yintp.jmx.server.mbean;

/**
 * 该类名称必须与实现的接口的前缀保持一致，即MBean前面的名称
 * 并且需要和实现的接口在同一包内
 *
 * @author yintp
 */
public class Hello implements HelloMBean {
    private String message;

    public Hello() {
        message = "Hello there";
    }

    public Hello(String message) {
        this.message = message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void sayHello() {
        System.out.println(message);
    }

    @Override
    public void sayHaha(String haha) {
        System.out.println(haha);
    }
}
