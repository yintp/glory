package com.yintp.service.provider.framework.impl.oracle;

import com.yintp.service.provider.framework.api.ProviderInterface;
import com.yintp.service.provider.framework.api.Service;
import com.yintp.service.provider.framework.api.ServiceManager;

/**
 * Oracle服务提供者的实现
 *
 * @author yintp
 */
public class OracleProviderImpl implements ProviderInterface {
    static {
        ServiceManager.registerProvider("oracleService", new OracleProviderImpl());
    }

    @Override
    public Service getService() {
        return new OracleServiceImpl();
    }
}
