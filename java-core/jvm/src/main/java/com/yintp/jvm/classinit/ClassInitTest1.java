package com.yintp.jvm.classinit;

/**
 * 对于静态变量来说，只有直接定义了该变量的类才会被初始化
 */
public class ClassInitTest1 {
    public static void main(String[] args) {
        /**
         * 依次输出Parent static block、Parent static variable
         * 这里的str1是父类进行定义的，这里主动使用了父类，但是没有主动使用子类，因此子类没有被初始化，最终不会执行子类中的静态代码块
         */
        System.out.println(Child1.str);
    }
}

class Parent1 {
    static String str = "Parent static variable";

    static {
        System.out.println("Parent static block");
    }
}

class Child1 extends Parent1 {
    static {
        System.out.println("Child static block");
    }
}
