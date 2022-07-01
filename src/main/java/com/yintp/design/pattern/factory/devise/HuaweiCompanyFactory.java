package com.yintp.design.pattern.factory.devise;

import com.yintp.design.pattern.factory.simple.Computer;
import com.yintp.design.pattern.factory.simple.HuaweiComputer;

/**
 * @author yintp
 */
public class HuaweiCompanyFactory extends AbstractCompanyFactory {
    @Override
    public Computer getComputer() {
        return new HuaweiComputer();
    }

    @Override
    public Phone getPhone() {
        return new HuaweiPhone();
    }
}
