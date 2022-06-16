package com.yintp.agent.api;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * 运行时加载instrument agent过程：
 * 通过JVM的attach机制来请求目标JVM加载对应的agent，过程大致如下：
 * 1、创建并初始化JPLISAgent（Java Programming Language Instrumentation Services Agent）；
 * 2、解析javaagent里MANIFEST.MF里的参数；
 * 3、创建InstrumentationImpl对象；
 * 4、监听ClassFileLoadHook事件；
 * 5、调用InstrumentationImpl的loadClassAndCallAgentmain方法，在这个方法里会去调用javaagent里MANIFEST.MF里指定的Agent-Class类的agentmain方法。
 *
 * @author yintp
 */
public class AgentMainAgent {
    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        inst.addTransformer(new TraceTransformer(), true);
        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        for (Class allLoadedClass : allLoadedClasses) {
            if (allLoadedClass.getSimpleName().indexOf("BusinessService") != -1) {
                inst.retransformClasses(allLoadedClass);
            }
        }
    }
}
