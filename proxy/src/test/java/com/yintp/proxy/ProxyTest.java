package com.yintp.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author yintp
 */
public class ProxyTest {
    @Test
    public void testJavaProxy() {
        Subject subject = (Subject) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{Subject.class}, (proxy, method, args) -> {
            System.out.println("PreProcess");
            Object result = method.invoke(new RealSubject(), args);
            System.out.println("PostProcess");
            return result;
        });
        subject.hello("welcome to beijing");
    }

    @Test
    public void testJavaProxyByHandler() {
        Subject subject = (Subject) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{Subject.class},
            new ProxyHandler(new RealSubject()));
        subject.hello("welcome to beijing");
    }
}
