package com.yintp.spring.framework.annotation.test;

import com.yintp.spring.framework.annotation.config.PropertyConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class PropertyConfigTest {


    private AnnotationConfigApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new AnnotationConfigApplicationContext(PropertyConfig.class);
    }

    @Test
    public void testValue() {
        printBeans();
        Object man = applicationContext.getBean("man");
        System.out.println("an===> " + man);
    }

    @Test
    public void testSpel() {
        Object banana = applicationContext.getBean("banana");
        System.out.println("banana===> " + banana);
    }

    private void printBeans() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames).stream().forEach(System.out::print);
    }
}
