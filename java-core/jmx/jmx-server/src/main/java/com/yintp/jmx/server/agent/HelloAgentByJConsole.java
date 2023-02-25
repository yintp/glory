package com.yintp.jmx.server.agent;

import com.yintp.jmx.server.mbean.Hello;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * 访问方式一：通过Jconsole访问
 * 1、通过本地jdk安装bin路径下找到jconsole.exe工具并运行
 * 2、双击打开我们的本地进程：HelloAgent
 * 3、在这个界面上，我们可以给程序中HelloMBean的属性赋值，也可以调用其中的方法
 * 访问方式二：通过JMX提供的工具页访问，由于找不到依赖jar包，不作demo演示，官网地址：https://www.oracle.com/technical-resources/articles/javase/jmx.html
 *
 * @author yintp
 */
public class HelloAgentByJConsole {
    public static void main(String[] args) throws JMException, InterruptedException {
        // 通过工厂类获取MBeanServer，用来做MBean的容器
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        // 创建HelloMBean，并注册
        // ObjectName格式为：“域名：name=MBean名称”，其中域名和MBean的名称可以任意取，用来唯一标识我们定义的这个MBean的实现类
        server.registerMBean(new Hello(), new ObjectName("jmxBean:name=hello"));
        Thread.sleep(10 * 60 * 1000);
    }
}
