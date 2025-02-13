package com.yintp.jvm.classinit;

/**
 * 1、java程序对类的使用方式分为主动使用和被动使用
 * 2、所有java虚拟机实现必须在每个类或接口被java程序首次主动使用时才初始化它们
 * 主动使用：
 * 使用 new 关键字创建类的实例
 * 访问某个类或接口的静态变量 getstatic（助记符），或者对该静态变量赋值 putstatic（被 final 修饰，已在编译期将结果放入常量池的静态字段除外）只有直接定义静态字段的类才会被初始化
 * 调用一个类的静态方法 invokestatic
 * 使用 java.lang.reflect 包对类进行反射（例如使用Class.forName(“com.test.Test”)）
 * 初始化一个类的子类，就会触发父类的初始化
 * Java 虚拟机启动时被标明启动类的类 ：即包含 main 方法的类
 * jdk1.7开始提供动态语言的支持java.lang.invoke.MethodHandle实例解析结果REF_getStatic，REF_putStatic，REF_invokeStatic句柄对应的类没有初始化，则初始化
 * 被动使用：
 * 除了上面七种情况外，其他使用Java类的方式都被看做是对类的被动使用，都不会导致类的初始化（但是加载和连接的操作可能发生）
 */
public class ClassInitTest {
}
