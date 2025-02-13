package com.yintp.spring.framework.annotation.test;

import com.andy.spring.framework.annotation.bean.*;
import com.yintp.spring.framework.annotation.bean.PersonDAO;
import com.yintp.spring.framework.annotation.bean.PersonService;
import com.yintp.spring.framework.annotation.config.AutowiredConfig;
import com.yintp.spring.framework.annotation.bean.Boss;
import com.yintp.spring.framework.annotation.bean.Car;
import com.yintp.spring.framework.annotation.bean.Color;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class AutowiredConfigTest {

    private AnnotationConfigApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new AnnotationConfigApplicationContext(AutowiredConfig.class);
    }

    @Test
    public void testAutowired2() {
        Boss boss = (Boss) applicationContext.getBean("boss");
        System.out.println(boss);
        Car car = (Car) applicationContext.getBean("car");
        System.out.println(car);
        Color color = (Color) applicationContext.getBean("color");
        System.out.println(color);
    }

    @Test
    public void testAutowired() {
        printBeans();
        PersonService personService = (PersonService) applicationContext.getBean("com.andy.java.spring.annotation.bean.PersonService");
        System.out.println("personService===> " + personService);
        personService.print();
        PersonDAO personDAO = (PersonDAO) applicationContext.getBean("com.andy.java.spring.annotation.bean.PersonDAO");
        System.out.println(personDAO);
    }

    private void printBeans() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames).stream().forEach(System.out::print);
    }
}
