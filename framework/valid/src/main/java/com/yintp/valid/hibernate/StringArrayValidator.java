package com.yintp.valid.hibernate;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringArrayValidator implements ConstraintValidator<StringArray, String> {

    private String[] values;

    @Override
    public void initialize(StringArray annotation) {
        this.values = annotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        } else {
            return ArrayUtils.contains(values, value.trim());
        }
    }
}
