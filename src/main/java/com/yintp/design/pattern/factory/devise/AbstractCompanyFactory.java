package com.yintp.design.pattern.factory.devise;

import com.yintp.design.pattern.factory.simple.Computer;

/**
 * @author yintp
 */
public abstract class AbstractCompanyFactory {
    public abstract Computer getComputer();

    public abstract Phone getPhone();
}
