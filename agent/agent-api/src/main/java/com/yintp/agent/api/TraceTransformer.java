package com.yintp.agent.api;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 可以在类加载之前，重写字节码
 *
 * @author yintp
 */
public class TraceTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.indexOf("BusinessService") != -1) {
            ClassPool pool = new ClassPool(true);
            pool.appendClassPath(new LoaderClassPath(loader));
            try {
                CtClass cls = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod[] methods = cls.getDeclaredMethods();
                for (CtMethod method : methods) {
                    method.addLocalVariable("startTime", CtClass.longType);
                    String codeStrBefore = "startTime=System.currentTimeMillis();";
                    String codeStrAfter = "System.out.println(\"method: " + method.getName() + " cost \" + (System.currentTimeMillis() - startTime) + \"ms\");";
                    // 在目标方法前后，插入代码
                    method.insertBefore(codeStrBefore);
                    method.insertAfter(codeStrAfter);
                }
                File file = new File("D:\\", cls.getSimpleName() + ".class");
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    fileOutputStream.write(cls.toBytecode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return cls.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 如果返回null则字节码不会被修改
        return null;
    }
}
