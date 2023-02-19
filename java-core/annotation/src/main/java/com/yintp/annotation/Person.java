package com.yintp.annotation;

import java.lang.annotation.*;

/**
 * 注解也叫元数据，一种分散式的元数据，与源代码绑定。是JDK1.5之后引入的一个特性，用于对代码进行说明，可以对包、类、接口、字段、方法参数、局部变量等进行注解。
 * 一般分为三种：
 * 1、java自带的标准注解 ，包括@Override（标明重写某个方法）、@Deprecated（标明某个类或方法过时）和@SuppressWarnings（标明要忽略的警告），使用这些注解后编译器就会进行检查；
 * 2、元注解，元注解是用于定义注解的注解，包括@Retention（标明注解被保留的阶段）、@Target（标明注解使用的范围）、@Inherited（标明注解可继承）、@Documented（标明是否生成javadoc文档）；
 * 3、自定义注解，可以根据自己的需求定义。
 * 主要用途和原理：
 * 1、生成文档，通过代码里的标识的元数据生成javadoc文档；
 * 2、编译检查，通过代码里表示的元数据让编译器在编译期间检查验证；
 * 3、编译时动态处理，编译时通过代码里标识的元数据动态处理，例如使用apt动态生成代码；
 * 4、运行时动态处理，运行时通过代码里标识的元数据动态处理，例如使用反射注入实例。
 * 核心元注解：
 * 1、@Target，指明作用目标元素类型；
 * 2、@Retention，定义了生命周期；
 * 3、@Documented，用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化，Documented是一个标记注解，没有成员；
 * 4、@Inherited，是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的，如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类；
 * class文件：
 * 1、javap -p Person.class
 * public interface com.yintp.annotation.Person extends java.lang.annotation.Annotation {
 * public abstract java.lang.String role();
 * }
 * 2、发现本质是一个接口
 *
 * @author yintp
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Persons.class)
public @interface Person {
    String role() default "";
}
