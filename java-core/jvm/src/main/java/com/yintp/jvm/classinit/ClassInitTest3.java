package com.yintp.jvm.classinit;

/**
 * 字面常量在编译阶段会存入到调用这个常量的方法所在的类的常量池中，以后该类对该常量的使用都会转换为对自身常量池的引用；
 * 本质上，调用类并没有直接引用到定义常量的类，因此并不会触发定义常量的类的初始化（所以不会执行静态代码块）
 * 注意：这里指的是将常量存到MyTest2的常量池中，之后MyTest2和MyParent2 就没有任何关系了。
 * 甚至我们可以将MyParent2的class文件删除（编译完之后），程序还可以执行。
 */
public class ClassInitTest3 {
    public static void main(String[] args) {
        /**
         * 输出hello constant
         */
        System.out.println(Constant3.str);
    }
}

class Constant3 {
    public static final String str = "hello constant";

    static {
        System.out.println("Constant static block");
    }
}
