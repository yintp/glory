package com.yintp.annotation.server;

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
