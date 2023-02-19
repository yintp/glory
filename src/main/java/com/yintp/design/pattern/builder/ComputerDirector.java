package com.yintp.design.pattern.builder;

/**
 * @author yintp
 */
public class ComputerDirector {
    public void makeComputer(ComputerBuilder builder) {
        builder.setUsbCount();
        builder.setDisplay();
        builder.setKeyboard();
    }
}
