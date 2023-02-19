package com.yintp.annotation;

import java.lang.annotation.*;

/**
 * @author yintp
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Persons {
    Person[] value() default {};
}
