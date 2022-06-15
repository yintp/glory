package com.yintp.agent.api;

import java.lang.instrument.Instrumentation;

/**
 * JVM启动时会先执行premain方法，大部分类加载都会通过该方法
 * 例如：系统类优先agent执行除外
 *
 * @author yintp
 */
public class PreMainAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new TraceTransformer(), true);
    }
}
