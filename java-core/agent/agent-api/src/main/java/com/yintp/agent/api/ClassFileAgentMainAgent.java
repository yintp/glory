package com.yintp.agent.api;


import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author zihao.yin
 * @since 2025/2/21 9:36
 */
public class ClassFileAgentMainAgent {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        String path = agentArgs;
        try {
            RandomAccessFile f = new RandomAccessFile(path, "r");
            final byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            final String clazzName = readClassName(bytes);
            // 加载
            for (Class clazz : inst.getAllLoadedClasses()) {
                if (clazz.getName().equals(clazzName)) {
                    ClassDefinition definition = new ClassDefinition(clazz, bytes);
                    inst.redefineClasses(definition);
                }
            }
        } catch (UnmodifiableClassException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 asm 读取类名
     */
    private static String readClassName(final byte[] bytes) {
        return new ClassReader(bytes).getClassName().replace("/", ".");
    }
}
