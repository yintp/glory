package com.yintp.proxy;

/**
 * @author yintp
 */
public class RealSubject implements Subject {
    @Override
    public void hello(String content) {
        System.out.println("hello: " + content);
    }
}
