package com.yintp.service.provider.framework.impl.oracle;

import com.yintp.service.provider.framework.api.Service;

/**
 * Oracle服务的实现
 *
 * @author yintp
 */
public class OracleServiceImpl implements Service {
    @Override
    public String getName() {
        return "oracle driver";
    }
}
