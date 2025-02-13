package com.yintp.spring.framework.annotation.test;

import com.yintp.spring.framework.annotation.bean.Data;
import com.yintp.spring.framework.annotation.config.ProfileConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class ProfileConfigTest {

    private AnnotationConfigApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("dev");
        applicationContext.register(ProfileConfig.class);
        applicationContext.refresh();
    }

    @Test
    public void testProfile() {
        printBeans();
        Data dev = (Data) applicationContext.getBean("devData");
        System.out.println(dev);
    }

    private void printBeans() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames).stream().forEach(System.out::println);
    }
}
