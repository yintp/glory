package com.yintp.spring.framework.annotation.aop.introduce;

import org.springframework.stereotype.Component;

@Component
public class SystemArchitectureImpl implements SystemArchitecture {

    @Override
    public void origin() {
        System.out.println("原始逻辑的方法被调用...");
    }
}
