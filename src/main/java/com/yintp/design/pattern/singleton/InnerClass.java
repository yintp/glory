package com.yintp.design.pattern.singleton;

/**
 * @author yintp
 */
public class InnerClass {
    private InnerClass() {

    }

    public static InnerClass getInstance() {
        return InnerClassHolder.instance;
    }

    private static class InnerClassHolder {
        private static final InnerClass instance = new InnerClass();
    }
}
