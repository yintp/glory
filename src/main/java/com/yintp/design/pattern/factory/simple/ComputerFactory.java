package com.yintp.design.pattern.factory.simple;

/**
 * @author yintp
 */
public class ComputerFactory {
    public static Computer getComputer(String type) {
        if ("huawei".equals(type)) {
            return new HuaweiComputer();
        } else if ("xiaomi".equals(type)) {
            return new XiaomiComputer();
        }
        return null;
    }
}
