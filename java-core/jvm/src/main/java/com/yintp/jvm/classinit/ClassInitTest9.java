package com.yintp.jvm.classinit;

/**
 * 入口函数所在的类初始化
 */
public class ClassInitTest9 {
    static {
        System.out.println("ClassInitTest9");
    }

    public static void main(String[] args) {
        //依次输出ClassInitTest9、Parent、9
        System.out.println(Child9.a);
    }
}

class Parent9 {
    static int a = 9;

    static {
        System.out.println("Parent");
    }
}

class Child9 extends Parent9 {
    static int b = 0;

    static {
        System.out.println("Child");
    }
}
