package com.yintp.valid.hibernate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StringArrayValidator.class)
public @interface StringArray {
    String[] value();

    String message() default "数据不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
