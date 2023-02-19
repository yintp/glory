package com.yintp.service.provider.framework.impl.mysql;

import com.yintp.service.provider.framework.api.ProviderInterface;
import com.yintp.service.provider.framework.api.Service;
import com.yintp.service.provider.framework.api.ServiceManager;

/**
 * Mysql服务提供者的实现
 *
 * @author yintp
 */
public class MysqlProviderImpl implements ProviderInterface {
    static {
        ServiceManager.registerProvider("mysqlService", new MysqlProviderImpl());
    }

    @Override
    public Service getService() {
        return new MysqlServiceImpl();
    }
}
