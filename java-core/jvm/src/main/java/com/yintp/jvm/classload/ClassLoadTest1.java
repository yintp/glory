package com.yintp.jvm.classload;

/**
 * -XX:+TraceClassLoading，用于追踪类的加载信息并打印出来
 * [Loaded com.andy.java.core.jvm.classload.ClassLoadTest1 from file:/D:/code/java/java-core/target/classes/]
 *
 * JVM参数使用方式大致有三种：
 * -XX:+<option>，表示开启option选项
 * -XX:-<option>，表示关闭option选项
 * -XX:<option>=<value>，表示给option选项赋值为value
 */
public class ClassLoadTest1 {

    public static void main(String[] args) {
        System.out.println("hello class load");
    }
}
