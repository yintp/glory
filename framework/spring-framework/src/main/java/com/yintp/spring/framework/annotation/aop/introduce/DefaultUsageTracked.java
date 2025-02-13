package com.yintp.spring.framework.annotation.aop.introduce;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class DefaultUsageTracked implements UsageTracked {

    @Override
    public void stronger() {
        System.out.println("引入的方法被调用...");
    }
}
