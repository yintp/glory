package com.yintp.design.pattern.builder;

/**
 * @author yintp
 */
public class Computer {
    /**
     * 必须
     */
    private String cpu;
    /**
     * 必须
     */
    private String ram;
    /**
     * 可选
     */
    private int usbCount;
    /**
     * 可选
     */
    private String keyboard;
    /**
     * 可选
     */
    private String display;

    public Computer(String cpu, String ram) {
        this.cpu = cpu;
        this.ram = ram;
    }

    public void setUsbCount(int usbCount) {
        this.usbCount = usbCount;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "Computer{" +
            "cpu='" + cpu + '\'' +
            ", ram='" + ram + '\'' +
            ", usbCount=" + usbCount +
            ", keyboard='" + keyboard + '\'' +
            ", display='" + display + '\'' +
            '}';
    }
}
