package com.yintp.jvm.classinit;

/**
 * 当一个类在初始化时，要求父类全部都已经初始化完毕
 */
public class ClassInitTest2 {

    public static void main(String[] args) {
        /**
         * 依次输出Parent static block、Child static block、Child static variable
         * 因为 str 是子类定义的，这里调用这句话就是对子类的主动调用，所以子类的静态代码块一定会执行，同时主动使用的时候，初始化
         * 子类的同时也会初始化父类；（初始化父类的子类，本质上对父类也是主动调用，而子类调用子类的静态变量，也是主动使用。）
         */
        System.out.println(Child3.str);
    }
}

class Parent3 {
    static {
        System.out.println("Parent static block");
    }
}

class Child3 extends Parent3 {
    static String str = "Child static variable";

    static {
        System.out.println("Child static block");
    }
}


