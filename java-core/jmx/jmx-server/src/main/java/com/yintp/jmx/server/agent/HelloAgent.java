package com.yintp.jmx.server.agent;

import com.yintp.jmx.server.listen.HelloListener;
import com.yintp.jmx.server.mbean.Hello;
import com.yintp.jmx.server.mbean.Yintp;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author yintp
 */
public class HelloAgent {
    public static void main(String[] args) throws JMException, Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Hello hello = new Hello();
        server.registerMBean(hello, new ObjectName("jmxBean:name=Hello"));
        Yintp yintp = new Yintp();
        server.registerMBean(yintp, new ObjectName("jmxBean:name=Yintp"));
        yintp.addNotificationListener(new HelloListener(), null, hello);
        Thread.sleep(10 * 60 * 1000);
    }
}
