package com.yintp.spring.framework.annotation.test;

import com.yintp.spring.framework.annotation.bean.Person;
import com.yintp.spring.framework.annotation.config.Config;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class ConfigTest {


    private ApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new AnnotationConfigApplicationContext(Config.class);
    }

    @Test
    public void testBean() {
        Person bean = applicationContext.getBean("person", Person.class);
        System.out.println("bean: " + bean);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        Arrays.asList(beanNamesForType).stream().forEach(System.out::print);
    }

    @Test
    public void testComponentScan() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames).stream().forEach(System.out::print);
    }

    @Test
    public void testScope() {
        Person bean1 = applicationContext.getBean("person", Person.class);
        Person bean2 = applicationContext.getBean("person", Person.class);
        boolean b = bean1 == bean2;
        System.out.println("bean1==bean2?" + b);
    }
}
