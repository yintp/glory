package com.yintp.jndi.client;

import com.yintp.jndi.api.ExploitObject;

import javax.naming.InitialContext;

/**
 * @author yintp
 */
public class RefApp {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        InitialContext initialContext = new InitialContext();
        Object lookup = initialContext.lookup("rmi://localhost:1099/yintp");
        System.out.println(lookup);
        ExploitObject s = (ExploitObject) lookup;
    }
}
