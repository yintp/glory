package com.yintp.jvm.classinit;

/**
 * 创建类的实例，属于主动使用，会导致类的初始化
 */
public class ClassInitTest5 {
    public static void main(String[] args) {
        /**
         * 输出Parent static block
         */
        Parent5 p = new Parent5();
    }
}

class Parent5 {
    static {
        System.out.println("Parent static block");
    }
}
