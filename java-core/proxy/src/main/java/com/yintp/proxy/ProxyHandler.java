package com.yintp.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author yintp
 */
public class ProxyHandler implements InvocationHandler {

    private Subject subject;

    public ProxyHandler(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("java dynamic proxy ProxyHandler PreProcess...");
        Object result = method.invoke(subject, args);
        System.out.println("java dynamic proxy ProxyHandler PostProcess...");
        return result;
    }
}
