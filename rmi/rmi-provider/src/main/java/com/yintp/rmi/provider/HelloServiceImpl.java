package com.yintp.rmi.provider;

import com.yintp.rmi.api.HelloService;
import com.yintp.rmi.api.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

/**
 * 远程接口实现类，继承UnicastRemoteObject，其可以作为远程对象，被注册到注册表中供客户端远程调用
 * 发布远程对象：
 * 1、默认调用父类UnicastRemoteObject的构造方法
 * 2、调用exportObject(Remote obj, int port)方法
 * 内部调用了exportObject(obj, new UnicastServerRef(port))，UnicastServerRef对象引用了LiveRef(tcp socket通信)
 * 3、UnicastServerRef.exportObject(obj, null, false)
 * 3.1、Remote var5 = Util.createProxy(var4, this.getClientRef(), this.forceStubUse);创建远程对象的代理类
 * 3.2、通过(Remote)Proxy.newProxyInstance(var4, var5, var6);
 * 3.3、RemoteObjectInvocationHandler代理逻辑，通过invokeRemoteMethod(Object proxy,Method method,Object[] args)中RemoteRef ref引用调用
 * 4、Target var6 = new Target(var1, this, var5, this.ref.getObjID(), var3);
 * this.ref.exportObject(var6);
 * 包装实际对象，并暴露在tcp端口上，等待客户端调用
 *
 * @author yintp
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    public HelloServiceImpl() throws RemoteException {
    }

    @Override
    public String say(User user) throws RemoteException {
        System.out.println("HelloServiceImpl say(User) invoke");
        return "hello, my name is " + Optional.ofNullable(user).orElseGet(User::new).getUsername();
    }
}
