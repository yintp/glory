package com.yintp.jvm.classinit;

/**
 * 当一个接口在初始化时，并不要求其父接口都完成了初始化
 * 只有在真正使用到父接口的时候（如引用接口中定义的常量），才会初始化
 * <p>
 * 重要：如果在一个接口中声明一个常量（b = 5）,而且该常量在编译期就能完全确定好具体的数值，那么就不会加载这个接口，而是直接把这个常量值直接纳入了 ClassInitTest7 的常量池中
 * 验证：添加 VM options 之后，然后编译运行发现根本没有加载 MyParent5和 MyChild5，仅仅加载了 MyTest5，同时将两者的 class 文件删除之后仍然可以运行
 */
public class ClassInitTest7 {
    public static void main(String[] args) {
        /**
         * 输出仅为 7
         */
        // System.out.println(Child7.b);
        /**
         * 依次输出 I'm A child static code block、com.andy.java.core.jvm.classinit.Child7For@1b6d3586
         */
        // System.out.println(Child7.c);
        /**
         * 依次输出 I'm A parent static code block、com.andy.java.core.jvm.classinit.Parent7For@1b6d3586
         */
        System.out.println(Child7.p);
    }
}

interface Parent7 {
    int a = 6;
    Parent7For p = new Parent7For();
}

interface Child7 extends Parent7 {
    int b = 7;
    Child7For c = new Child7For();
}

class Parent7For {
    static {
        System.out.println("I'm A parent static code block");
    }
}

class Child7For {
    static {
        System.out.println("I'm A child static code block");
    }
}
