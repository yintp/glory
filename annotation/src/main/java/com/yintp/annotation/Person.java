package com.yintp.annotation;

import java.lang.annotation.*;

/**
 * @author yintp
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Persons.class)
public @interface Person {
    String role() default "";
}
