package com.yintp.spi.impl.file;

import com.yintp.spi.api.service.SearchService;

/**
 * 文件搜索实现
 *
 * @author yintp
 */
public class FileSearchServiceImpl implements SearchService {
    @Override
    public String search(String keyword) {
        return "file search, key:" + keyword + ", value: linux";
    }
}
