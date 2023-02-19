package com.yintp.proxy;

import java.lang.reflect.Proxy;

/**
 * 1、通过Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)获取代理类对象
 * 1.1、通过Proxy.getProxyClass0(ClassLoader loader,Class<?>... interfaces)获取代理类Class
 * 1.2、通过反射cons.newInstance(new Object[]{h})创建代理类对象$Proxy0.class
 * $Proxy0 extends Proxy implements Subject所以也就决定了java动态代理只能对接口进行代理，Java的单继承机制注定了这些动态代理类们无法实现对class的动态代理
 *
 * @author yintp
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        RealSubject realSubject = new RealSubject();
        Subject subject = (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), new Class<?>[]{Subject.class},
            new ProxyHandler(realSubject));
        subject.hello("welcome to beijing");
    }
}
