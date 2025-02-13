package com.yintp.jvm.classinit;

import java.util.UUID;

/**
 * 当一个常量的值并非编译期间可以确定的，那么其值就不会放到调用类的常量池中。
 * 这时在程序运行时，会导致主动使用这个常量所在的类，显然会导致这个类被初始化。
 */
public class ClassInitTest4 {
    public static void main(String[] args) {
        /**
         * 输出Constant static block、5a79eee7-46bc-4d6a-b81c-4ec5fa875528
         */
        System.out.println(Constant4.str);
    }
}

class Constant4 {
    public static final String str = UUID.randomUUID().toString();

    static {
        System.out.println("Constant static block");
    }
}


