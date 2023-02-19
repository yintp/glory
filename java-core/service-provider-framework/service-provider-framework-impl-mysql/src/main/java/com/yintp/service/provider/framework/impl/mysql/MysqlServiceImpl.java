package com.yintp.service.provider.framework.impl.mysql;

import com.yintp.service.provider.framework.api.Service;

/**
 * Mysql服务的实现
 *
 * @author yintp
 */
public class MysqlServiceImpl implements Service {
    @Override
    public String getName() {
        return "mysql driver";
    }
}
