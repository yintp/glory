package com.yintp.apt.server;

import com.yintp.apt.api.SayHello;

/**
 * 会在calsses文件中生成SuperManHello.class文件
 * generated-sources/annotations目录下生成SuperManHello.java文件
 *
 * @author yintp
 */
@SayHello
public class SuperMan {
    public static void main(String[] args) {
        SuperManHello.sayHello();
    }
}
