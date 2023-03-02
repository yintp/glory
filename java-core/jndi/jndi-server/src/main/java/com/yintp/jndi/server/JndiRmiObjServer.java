package com.yintp.jndi.server;

import com.yintp.jndi.api.Person;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;

/**
 * jndi是Java命名和目录接口（Java Naming and Directory Interface，JNDI）的简称.
 * 从一开始就一直是Java 2平台企业版的核心技术之一。在JMS，JMail,JDBC,EJB等技术中，就大量应用的这种技术。
 * JNDI可访问的现有的目录及服务有：DNS、XNam 、Novell目录服务、LDAP(Lightweight Directory Access Protocol 轻型目录访问协议)、 CORBA对象服务、文件系统、Windows XP/2000/NT/Me/9x的注册表、RMI、DSML v1&v2、NIS。
 * jndi诞生的理由似乎很简单。随着分布式应用的发展，远程访问对象访问成为常用的方法。虽然说通过Socket等编程手段仍然可实现远程通信，但按照模式的理论来说，仍是有其局限性的。RMI技术，RMI-IIOP技术的产生，使远程对象的查找成为了技术焦点。JNDI技术就应运而生。JNDI技术产生后，就可方便的查找远程或是本地对象。
 * <p>
 * 架构：
 *     jndi api
 *         ↓
 *   naming manager
 *         ↓
 *     jndi spi
 *         ↓
 *    spi impl(ldap、dns、rmi...)
 * <p>
 * 术语：
 * Name：就是命名。将Java对象以某个名称的形式绑定（binding）到一个容器环境（Context）中，以后调用容器环境（Context）的查找（lookup）方法又可以查找出某个名称所绑定的Java对象。简单来说，就是把一个Java对象和一个特定的名称关联在一起，方便容器后续使用。
 * Directory：是指将一个对象的所有属性信息保存到一个容器环境中。JNDI的目录（Directory）原理与JNDI的命名（Naming）原理非常相似，主要的区别在于目录容器环境中保存的是对象的属性信息，而不是对象本身。举个例子，Name的作用是在容器环境中绑定一个Person对象，而Directory的作用是在容器环境中保存这个Person对象的属性，比如说age=10，name=小明等等。实际上，二者往往是结合在一起使用的。
 *
 * @author yintp
 */
public class JndiRmiObjServer {
    public static void main(String[] args) throws Exception {
        InitialContext ctx = null;
        try {
            LocateRegistry.createRegistry(1099);
            // 配置JNDI工厂和JNDI的url和端口。如果没有配置这些信息，将会出现NoInitialContextException异常。
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            System.setProperty(Context.PROVIDER_URL, "rmi://localhost:1099");
            // 初始化
            ctx = new InitialContext();
            Person p = new Person();
            p.setName("yintp");
            // 将person对象绑定到JNDI服务中，JNDI的名字叫做：person
            ctx.bind("person", p);
            Thread.sleep(1000 * 3600);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }
}
