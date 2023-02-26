package com.yintp.jmx.client;

import com.yintp.jmx.server.mbean.HelloMBean;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

/**
 * @author yintp
 */
public class Client {
    public static void main(String[] args) throws IOException, Exception, NullPointerException {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        String[] domains = mbsc.getDomains();
        for (int i = 0; i < domains.length; i++) {
            System.out.println("Domain[" + i + "]=" + domains[i]);
        }
        System.out.println("MBean count = " + mbsc.getMBeanCount());

        // ObjectName的名称与前面注册时候的保持一致
        ObjectName mbeanName = new ObjectName("jmxBean:name=hello");
        // 设置指定Mbean的特定属性值，这里的setAttribute、getAttribute操作只能针对bean的属性，例如对getName或者setName进行操作，只能使用Name，需要去除方法的前缀
        mbsc.setAttribute(mbeanName, new Attribute("Message", "yintp"));
        String message = (String) mbsc.getAttribute(mbeanName, "Message");
        System.out.println("message=" + message);
        // invoke调用bean的方法，只针对非设置属性的方法，例如invoke不能对getName方法进行调用
        mbsc.invoke(mbeanName, "sayHello", null, null);
        mbsc.invoke(mbeanName, "sayHaha", new String[]{"haha"}, new String[]{"java.lang.String"});

        HelloMBean proxy = MBeanServerInvocationHandler.newProxyInstance(mbsc, mbeanName, HelloMBean.class, false);
        proxy.sayHello();
        proxy.sayHaha("haha");
    }
}
