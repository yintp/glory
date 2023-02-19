package com.yintp.design.pattern.builder;

/**
 * @author yintp
 */
public class HuaweiComputerBuilder extends ComputerBuilder {
    private Computer computer;

    public HuaweiComputerBuilder(String cpu, String ram) {
        computer = new Computer(cpu, ram);
    }

    @Override
    public void setUsbCount() {
        computer.setUsbCount(2);
    }

    @Override
    public void setKeyboard() {
        computer.setKeyboard("华为键盘");
    }

    @Override
    public void setDisplay() {
        computer.setDisplay("华为显示器");
    }

    @Override
    public Computer getComputer() {
        return computer;
    }
}
