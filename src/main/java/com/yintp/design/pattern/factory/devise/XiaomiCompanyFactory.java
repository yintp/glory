package com.yintp.design.pattern.factory.devise;

import com.yintp.design.pattern.factory.simple.Computer;
import com.yintp.design.pattern.factory.simple.XiaomiComputer;

/**
 * @author yintp
 */
public class XiaomiCompanyFactory extends AbstractCompanyFactory {
    @Override
    public Computer getComputer() {
        return new XiaomiComputer();
    }

    @Override
    public Phone getPhone() {
        return new XiaomiPhone();
    }
}
