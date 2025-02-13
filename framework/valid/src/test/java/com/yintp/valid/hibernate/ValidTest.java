package com.yintp.valid.hibernate;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

public class ValidTest {

    @Test
    public void testValiBean() {
        // 获取目标对象实例
        PersonReqDTO personReqDTO = getPersonReqDTO();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PersonReqDTO>> violations = validator.validate(personReqDTO);
        violations.stream().forEach(x -> System.out.println("校验信息：" + x.getMessage()));
    }

    @Test
    public void testValidMethodParam() throws NoSuchMethodException {
        // 获取到目标类的实例
        PersonService personService = new PersonServiceImpl();
        // 获取目标类需要验证的方法
        Method method = personService.getClass().getMethod("create", PersonReqDTO.class, String.class);
        // 获取目标方法的入参
        Object[] parameterValues = {getPersonReqDTO(), "yintp"};

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<PersonService>> violations = validator.forExecutables().validateParameters(personService, method, parameterValues, new Class<?>[0]);
        violations.stream().forEach(x -> System.out.println("校验信息：" + x.getMessage()));
    }

    @Test
    public void testValidMethodReturn() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 获取到目标类的实例
        PersonService personService = new PersonServiceImpl();
        // 获取目标类需要验证的方法
        Method method = personService.getClass().getMethod("create");
        // 获取目标方法的返回值
        Object returnValue = method.invoke(personService);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<PersonService>> violations = validator.forExecutables().validateReturnValue(personService, method, returnValue, new Class<?>[0]);
        violations.stream().forEach(x -> System.out.println("校验信息：" + x.getMessage()));
    }

    private PersonReqDTO getPersonReqDTO() {
        PersonReqDTO personReqDTO = new PersonReqDTO();
        personReqDTO.setIdName("张小三");
        personReqDTO.setAge(-1);
        PersonReqDTO.BirthDay birthDay = new PersonReqDTO.BirthDay();
        birthDay.setYear(1992);
        birthDay.setMonth(13);
        birthDay.setDay(32);
        birthDay.setBirthDay(Date.from(LocalDate.of(2019, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        birthDay.setDoomsday(Date.from(LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        personReqDTO.setBirthDay(birthDay);
        personReqDTO.setSign("时间是一只永远在飞的鸟");
        personReqDTO.setSex("男");
        return personReqDTO;
    }
}
