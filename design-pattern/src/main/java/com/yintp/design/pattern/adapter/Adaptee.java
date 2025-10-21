package com.yintp.design.pattern.adapter;

/**
 * 被适配者：已经存在的类，但接口不匹配
 *
 * @author zihao.yin
 */
public class Adaptee {
    public void specificRequest() {
        System.out.println("被适配者：执行特定的请求");
    }
}
