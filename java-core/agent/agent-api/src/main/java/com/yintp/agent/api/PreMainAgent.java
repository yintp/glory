package com.yintp.agent.api;

import java.lang.instrument.Instrumentation;

/**
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
