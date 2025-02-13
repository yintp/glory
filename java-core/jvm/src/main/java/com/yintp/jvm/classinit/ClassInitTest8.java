package com.yintp.jvm.classinit;

/**
 * 准备和初始化阶段静态变量赋值问题
 * 在准备阶段，Java虚拟机为类的静态变量分配内存，并设置默认的初始值==，这些变量使用的内存都将在方法区进行分配。这里进行内存分配的仅仅包括类变量（被 static 修饰的变量），但是不包括实例变量；实例变量将在对象实例化时候随着对象一起分配在 Java 堆中；
 * <p>
 * 特殊：如果类字段的字段属性表中存在 ConstantValue 属性，则在准备阶段变量值就会被初始化为 ConstantValue 属性所指定的值；
 */
public class ClassInitTest8 {
    public static void main(String[] args) {
        System.out.println(Singleton8.getInstance());
        System.out.println(Singleton8.a);//1
        System.out.println(Singleton8.b);//0
        System.out.println(ClassInitTest8.class.getClassLoader());
    }
}

class Singleton8 {

    public static int a;

    private static Singleton8 instance = new Singleton8();

    private Singleton8() {
        a++;
        b++;
        System.out.println(a);//1
        System.out.println(b);//1
    }

    public static int b = 0;

    public static Singleton8 getInstance() {
        return instance;
    }
}
