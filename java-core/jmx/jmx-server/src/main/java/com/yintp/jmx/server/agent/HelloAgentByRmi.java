package com.yintp.jmx.server.agent;

import com.yintp.jmx.server.mbean.Hello;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

/**
 * @author yintp
 */
public class HelloAgentByRmi {
    public static void main(String[] args) throws Exception {
        // 通过工厂类获取MBeanServer，用来做MBean的容器
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        // 创建HelloMBean，并注册
        // ObjectName格式为：“域名：name=MBean名称”，其中域名和MBean的名称可以任意取，用来唯一标识我们定义的这个MBean的实现类
        server.registerMBean(new Hello(), new ObjectName("jmxBean:name=hello"));

        // 注册一个端口，绑定url后用于客户端通过rmi方式连接JMXConnectorServer
        LocateRegistry.createRegistry(9999);
        // URL路径的结尾可以随意指定，但如果需要用Jconsole来进行连接，则必须使用jmxrmi
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
        JMXConnectorServer jcs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
        jcs.start();
    }
}
