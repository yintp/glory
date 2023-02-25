package com.yintp.jmx.server.mbean;

/**
 * JMX是管理扩展，通过JMX我们可以监控管理我们的指定的java程序。但不是所有的java类都能被管理。
 * 术语描述：
 * 1、MBean：是Managed Bean的简称，在JMX中MBean代表一个被管理的资源实例，通过MBean暴露一系列的方法和属性，外界可以获取被管理的资源的状态和操纵MBean的行为。
 * 事实上，MBean就是一个Java Object。同javabean模型一样，外界使用反射获取Object的值和调用Object的方法，只是MBean提供了更加容易操作的反射的使用。
 * MBean包括4种类型：标准MBean、动态MBean，开放MBean，模型MBean。
 * 2、MBeanServer：MBeanServer是MBean的容器。MBeanServer管理这些MBean，并且通过代理外界对它们的访问。
 * MBeanServer提供了一种注册机制，通过注册Adaptor和Connector，以及MBean到MBeanServer，并且通过代理外界对它们的访问，外界可以通过名字来得到相应的MBean实例。
 * 3、JMX Agent：Agent只是一个java进程，它包括这个MBeanServer和一系列附加的MBeanService。当然这些Service也是通过MBean的形式来发布。
 * 4、Protocol Adaptors and Connectors：JMX Agent通过各种各样的Adaptor和Connector来与外界（JVM之外）进行通信。
 * 同样外界（JVM之外）也必须通过某个Adaptor和Connector向来JMX Agent发送管理或控制请求。jdk5.1中，sun提供很多Adaptor和Connector的实现。
 * <p>
 * Adaptor和Connector的区别在于：Adaptor是使用某种协议（Http和SNMP）来与JMX Agent获得联系，Agent端会有一个对象（Adaptor）来处理有关协议的细节。
 * 比如SNMP Adaptor和HTTP Adapter而Connector在Agent端和client端都必须有这样一个对象来处理响应的请求和应答。比如RMI Connector。
 * JMX Agent可以带有任意多个Adaptor，因此可以使用多种不同的方式访问Agent。
 * <p>
 * JMX基本构架：JMX分为三层，分别负责处理不同的事务。它们分别是：
 * 1、Instrumention层：Instrumention层主要包括了一系列的接口定义和描述如何开发MBean的规范。通常JMX所管理的资源有一个或多个MBean组成，因此这个资源可以是任何由Java语言开发的组件，或是一个JavaWrapper包装的其他语言开发的资源。
 * 2、Agent层：Agent用来管理相应的资源，并且为远端用户提供访问的接口。Agent层构建在Intrumentation层之上，并且使用管理Instrumentation层内部的组件。通常Agent由一个MBeanServer组成。另外Agent还提供一个或多个Adapter或Connector以供外界的访问。
 * 3、Distributed层：Distributed层关心Agent如何被远端用户访问的细节。它定义了一系列用来访问Agent的接口和组件，包括Adapter和Connector的描述。
 * <p>
 * 应用场景：
 * 1、监控应用程序的运行状态和统计信息
 * 2、修改应用的配置信息
 * 3、状态变化通知处理
 * 4、hibernate、Tomcat、JBoss
 * <p>
 * linux下利用JMX监控Tomcat:
 * -Dcom.sun.management.jmxremote=true                   相关 JMX 代理侦听开关
 * -Djava.rmi.server.hostname                            服务器端的IP
 * -Dcom.sun.management.jmxremote.port=29094             相关 JMX 代理侦听请求的端口
 * -Dcom.sun.management.jmxremote.ssl=false              指定是否使用 SSL 通讯
 * -Dcom.sun.management.jmxremote.authenticate=false     指定是否需要密码验证
 * <p>
 * HelloMBean接口，接口的命名规范为以具体的实现类为前缀，MBean为后缀
 *
 * @author yintp
 */
public interface HelloMBean {
    public void setMessage(String message);

    public String getMessage();

    public void sayHello();

    public void sayHaha(String haha);
}
