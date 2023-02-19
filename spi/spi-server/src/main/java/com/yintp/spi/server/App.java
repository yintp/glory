package com.yintp.spi.server;

import com.yintp.spi.api.service.SearchService;
import com.yintp.spi.server.search.SearchServiceFactory;

/**
 * @author yintp
 */
public class App {
    public static void main(String[] args) {
        SearchService searchService = SearchServiceFactory.newSearch();
        String result = searchService.search("hello,spi");
        System.out.println(result);
    }
}
