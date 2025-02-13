package com.yintp.jvm.classinit;

/**
 * 对于数组实例来说，其类型是由JVM在运行期动态生成的，表示为 [Lcom.andy.java.core.jvm.classinit.Parent6 这种形式。动态生成的类型，其父类型就是Object。
 * 对于数组来说，JavaDoc经构成数据的元素成为Component，实际上是将数组降低一个维度后的类型。
 */
public class ClassInitTest6 {
    public static void main(String[] args) {
        /**
         * 无任何输出
         * 当创建数组类型的实例，并不表示对数组中的元素的主动使用，而仅仅表示创建了这个数组的实例而已，数组new 出来的实例类型有由JVM在运行期动态生成的。
         */
        Parent6[] parent6s = new Parent6[1];
        /**
         * class [Lcom.andy.java.core.jvm.classinit.Parent6;
         */
        System.out.println(parent6s.getClass());
    }
}

class Parent6 {
    static {
        System.out.println("Parent static block");
    }
}
