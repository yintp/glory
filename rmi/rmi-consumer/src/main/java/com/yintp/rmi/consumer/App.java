package com.yintp.rmi.consumer;

import com.yintp.rmi.api.HelloService;
import com.yintp.rmi.api.User;

import java.rmi.Naming;

/**
 * 客户端获取服务端Registry代理：
 * 1、Naming.lookup("//127.0.0.1:1099/HelloService")方法
 * 2、通过LocateRegistry.getRegistry(parsed.host, parsed.port)获取通过LocateRegistry对象，
 * 2.1、构建RemoteRef对象，传入并创建RegistryImpl_Stub本地代理对象
 * 3、通过LocateRegistry.lookup(parsed.name)方法发送到服务端
 * 4、this.ref.invoke(var2);-》var1.executeCall();-》StreamRemoteCall.executeCall()方法，通过tcp连接发送消息到服务端
 *
 * @author yintp
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        try {
            // 找到远程对象的Stub存根对象
            HelloService helloService = (HelloService) Naming.lookup("//127.0.0.1:1099/HelloService");
            System.out.println(helloService.say(new User("yintp")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
