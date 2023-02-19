package com.yintp.design.pattern.builder;

/**
 * @author yintp
 */
public class XiaomiComputerBuilder extends ComputerBuilder {
    private Computer computer;

    public XiaomiComputerBuilder(String cpu, String ram) {
        computer = new Computer(cpu, ram);
    }

    @Override
    public void setUsbCount() {
        computer.setUsbCount(4);
    }

    @Override
    public void setKeyboard() {
        computer.setKeyboard("小米键盘");
    }

    @Override
    public void setDisplay() {
        computer.setDisplay("小米显示器");
    }

    @Override
    public Computer getComputer() {
        return computer;
    }
}
