package com.yintp.spi.impl.db;

import com.yintp.spi.api.service.SearchService;

/**
 * 数据库搜索实现
 *
 * @author yintp
 */
public class DbSearchServiceImpl implements SearchService {
    @Override
    public String search(String keyword) {
        return "db search, key:" + keyword + ", value: mysql";
    }
}
