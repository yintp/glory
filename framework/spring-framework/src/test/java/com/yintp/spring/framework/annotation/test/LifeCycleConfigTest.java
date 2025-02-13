package com.yintp.spring.framework.annotation.test;

import com.yintp.spring.framework.annotation.config.LifeCycleConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifeCycleConfigTest {

    private AnnotationConfigApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
    }

    @Test
    public void testBeanMethod() {
//        Car bean = applicationContext.getBean(Car.class);
//        log.info("bean{}", bean);
        applicationContext.close();
    }
}
