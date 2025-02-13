package com.yintp.spring.framework.annotation.aop.introduce;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UsageTracking {

    @DeclareParents(value = "com.yintp.spring.framework.annotation.aop.introduce.SystemArchitecture+", defaultImpl = DefaultUsageTracked.class)
    public static UsageTracked usageTracked;
}
