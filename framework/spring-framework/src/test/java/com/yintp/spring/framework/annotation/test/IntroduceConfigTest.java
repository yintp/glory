package com.yintp.spring.framework.annotation.test;

import com.yintp.spring.framework.annotation.aop.introduce.SystemArchitecture;
import com.yintp.spring.framework.annotation.aop.introduce.UsageTracked;
import com.yintp.spring.framework.annotation.config.IntroduceConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IntroduceConfigTest {

    private AnnotationConfigApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new AnnotationConfigApplicationContext(IntroduceConfig.class);
    }

    @Test
    public void testIntroduce() {
        SystemArchitecture systemArchitecture = (SystemArchitecture) applicationContext.getBean("systemArchitectureImpl");
        systemArchitecture.origin();
        UsageTracked usageTracked = (UsageTracked) systemArchitecture;
        usageTracked.stronger();
    }
}
