package com.yintp.agent.api;

import java.lang.instrument.Instrumentation;

/**
 * java agent是一种可以动态修改Java字节码的技术。
 * 应用场景：
 * IDE的调试功能，例如IntelliJ IDEA；
 * 热部署功能，例如spring-loaded；
 * 各种线上诊断工具，例如Arthas；
 * 各种性能分析工具，例如Visual VM、JConsole等；
 * 全链路性能检测工具，例如Skywalking；
 * 术语描述：
 * 1、JVMTI：JVM Tool Interface，是JVM暴露出来给用户扩展使用的接口集合，JVMTI是基于事件驱动的，JVM每执行一定的逻辑就会触发一些事件的回调接口，通过这些回调接口，用户可以自行扩展。
 * JVMTI是实现 Debugger、Profiler、Monitor、Thread Analyser 等工具的统一基础，在主流 Java 虚拟机中都有实现
 * 2、JVMTIAgent：是一个动态库，利用JVMTI暴露出来的一些接口来干一些我们想做、但是正常情况下又做不到的事情，不过为了和普通的动态库进行区分，它一般会实现如下的一个或者多个函数
 * - Agent_OnLoad函数，如果agent是在启动时加载的，通过JVM参数设置
 * - Agent_OnAttach函数，如果agent不是在启动时加载的，而是我们先attach到目标进程上，然后给对应的目标进程发送load命令来加载，则在加载过程中会调用Agent_OnAttach函数
 * - Agent_OnUnload函数，在agent卸载时调用
 * 3、javaagent：依赖于instrument的JVMTIAgent（Linux下对应的动态库是libinstrument.so），还有个别名叫JPLISAgent(Java Programming Language Instrumentation Services Agent)，专门为Java语言编写的插桩服务提供支持的
 * 4、instrument：实现了Agent_OnLoad和Agent_OnAttach两方法，也就是说在使用时，agent既可以在启动时加载，也可以在运行时动态加载。其中启动时加载还可以通过类似-javaagent:jar包路径的方式来间接加载instrument agent，运行时动态加载依赖的是JVM的attach机制，通过发送load命令来加载agent
 * 5、JVM Attach：是指JVM提供的一种进程间通信的功能，能让一个进程传命令给另一个进程，并进行一些内部的操作，比如进行线程dump，那么就需要执行jstack进行，然后把pid等参数传递给需要dump的线程来执行
 * <p>
 * JVM启动时会先执行premain方法，大部分类加载都会通过该方法
 * 例如：系统类优先agent执行除外
 * <p>
 * 启动时加载instrument agent过程：
 * 1、创建并初始化JPLISAgent（Java Programming Language Instrumentation Services Agent）；
 * 2、监听VMInit事件，在JVM初始化完成之后做下面的事情：
 * 2.1、创建InstrumentationImpl对象；
 * 2.2、监听ClassFileLoadHook事件；
 * 2.3、调用InstrumentationImpl的loadClassAndCallPremain方法，在这个方法里会去调用javaagent中MANIFEST.MF里指定的Premain-Class类的premain方法。
 * 3、解析javaagent中MANIFEST.MF文件的参数，并根据这些参数来设置JPLISAgent里的一些内容。
 *
 * @author yintp
 */
public class PreMainAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new TraceTransformer(), true);
    }
}
