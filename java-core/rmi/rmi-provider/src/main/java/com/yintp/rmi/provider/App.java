package com.yintp.rmi.provider;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * 服务端启动Registry服务：
 * 1、LocateRegistry.createRegistry(1099);
 * 2、通过new RegistryImpl(port)创建了RegistryImpl对象；
 * 3、判断端口是否是1099和系统开启了安全管理器，如果是在则限定的权限集内绕过系统的安全校验
 * 4、创建 LiveRef var2 = new LiveRef(id, var1)对象
 * 5、通过调用this.setup(new UnicastServerRef(var2, RegistryImpl::registryFilter))
 * 6、调用UnicastServerRef.exportObject(this, (Object)null, true);方法
 * 7、创建RegistryImpl代理对象（RegistryImpl_Stub）
 * 8、然后将UnicastServerRef的skel（skeleton）对象设置为当前RegistryImpl对象
 * 9、最后用skeleton、stub、UnicastServerRef对象、id和一个boolean值构造了一个Target对象，也就是这个Target对象基本上包含了全部的信息，等待TCP调用。
 * 10、调用UnicastServerRef的ref（LiveRef）变量的exportObject()方法
 * 11、LiveRef的exportObject()方法，TCPTransport的exportObject()方法，将Target对象暴露出去。
 * 12、调用TCPTransport的listen()方法，listen()方法创建了一个ServerSocket，并且启动了一线程等待客户端的请求。接着调用父类Transport的exportObject()将Target对象存放进ObjectTable中。
 *
 * @author yintp
 */
public class App {
    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        try {
            // 创建本地主机上的远程对象注册表Registry实例，并指定端口为1099
            LocateRegistry.createRegistry(1099);
            // banding远程对象到RIMI注册表上，rmi协议可以缺省
            Naming.bind("rmi://localhost:1099/HelloService", new HelloServiceImpl());
            System.out.println("启动远程RMI注册服务");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
