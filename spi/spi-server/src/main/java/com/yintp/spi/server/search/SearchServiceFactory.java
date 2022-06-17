package com.yintp.spi.server.search;

import com.yintp.spi.api.service.SearchService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author yintp
 */
public class SearchServiceFactory {
    private SearchServiceFactory() {
    }

    public static SearchService newSearch() {
        SearchService searchService = null;
        ServiceLoader<SearchService> serviceLoader = ServiceLoader.load(SearchService.class);
        Iterator<SearchService> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            searchService = iterator.next();
        }
        return searchService;
    }
}
