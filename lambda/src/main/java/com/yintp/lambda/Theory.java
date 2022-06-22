package com.yintp.lambda;

/**
 * 探究Java8中到底是如何实现Lambda表达式，Lambda表达式经过编译后是什么呢？
 * Java8底层是以内部类的方式来实现Lambda表达式的。
 *
 * 利用indy指令
 * 1、编译器会在每个Lambda表达式出现的地方插入一条indy指令，同时还必须在class文件里生成相应的CONSTANT_InvokeDynamic_info常量池项和BootstrapMethods属性等信息。
 * 2、当JVM第一次执行到这条indy指令的时候，它会找到这条指令相应的bootstrap方法，然后调用该方法，得到一个CallSite（LambdaMetafactory.metafactory(...)）。
 * InnerClassLambdaMetafactory的buildCallSite()中，会调用一个叫做spinInnerClass()的方法，此方法会使用字节码工具在内存中生成一个类。
 *
 * 1. javap -p Theory.class
 * Compiled from "Theory.java"
 * public class com.andy.java.core.lambda.Theory {
 *   private static com.andy.java.core.lambda.Print print;
 *   public com.andy.java.core.lambda.Theory();
 *   public static void printStr(java.lang.String);
 *   public static void main(java.lang.String[]);
 *   private static void lambda$printStr$1(java.lang.Object);
 *   private static void lambda$static$0(java.lang.Object);
 *   static {};
 * }
 * 2. JVM设置系统属性-Djdk.internal.lambda.dumpProxyClasses，spinInnerClass()方法会将生成的类保存到文件中
 * 3. 此类类似于下面：
 * public class Theory {
 *
 *     private static Print print = Class.forName("xxx.Theory$$Lambda$1").newInstance();
 *
 *     public static void printStr(String s) {
 *         Print p = Class.forName("xxx.Theory$$Lambda$2").newInstance();
 *         p.print(s);
 *         print.print(s);
 *     }
 *
 *     public static void main(String[] args) {
 *         printStr("hello lambda");
 *     }
 *
 *     private static void lambda$printStr$1(Object var1) {
 *          System.out.println(var1);
 *     }
 *
 *     private static void lambda$static$0(Object var1) {
 *         System.out.println(var1);
 *     }
 *
 *     final class Theory$$Lambda$1 implements Print {
 *         private Theory$$Lambda$1() {
 *         }
 *
 *         @Hidden
 *         public void print(Object var1) {
 *             Theory.lambda$static$0(var1);
 *         }
 *    }
 *
 *    final class Theory$$Lambda$2 implements Print {
 *         private Theory$$Lambda$2() {
 *         }
 *
 *         @Hidden
 *         public void print(Object var1) {
 *             Theory.lambda$printStr$1(var1);
 *         }
 *    }
 * }
 *
 * @author yintp
 */
public class Theory {
    private static Print print = (x) -> System.out.println(x);

    public static void printStr(String s) {
        Print p = (x) -> System.out.println(x);
        p.print(s);
        print.print(s);
    }

    public static void main(String[] args) {
        printStr("hello lambda");
    }
}
