package com.yintp.annotation;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class AnnotationTest {
    @Test
    public void testRepeatableAnnotation() {
        Class<SuperMan> clazz = SuperMan.class;
        Persons persons = clazz.getAnnotation(Persons.class);
        Person[] ps = persons.value();
        for (Person p : ps) {
            System.out.println("role: " + p.role());
        }
        Assert.assertTrue("断言是Persons注解不通过", clazz.isAnnotationPresent(Persons.class));
        Assert.assertFalse("断言不是Person注解不通过", clazz.isAnnotationPresent(Person.class));
    }
}
