package com.yintp.jndi.client;

import com.yintp.jndi.api.Person;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author yintp
 */
public class ObjApp {
    public static void main(String[] args) throws NamingException {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        System.setProperty(Context.PROVIDER_URL, "rmi://localhost:1099");
        InitialContext ctx = new InitialContext();
        // 通过lookup查找person对象
        Person person = (Person) ctx.lookup("person");
        System.out.println(person.toString());
    }
}
