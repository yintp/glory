package com.yintp.spi.server.search;

import com.yintp.spi.api.service.SearchService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * spi服务发现机制
 * 1、ServiceLoader.load(Class<S> service)
 * 2、获取当前线程的ContentClassLoader调用重载load方法
 *  2.1、创建ServiceLoader
 *  2.2、其中调用reload()
 *  2.3、创建LazyIterator：
 *      hasNextService()读取META-INF/services下的配置文件，获得所有能被实例化的类的名称，并完成SPI配置文件的解析
 *      nextService()负责实例化hasNextService()读到的实现类，并将实例化后的对象存放到providers集合中缓存
 * 3、获取ServiceLoader迭代器并遍历
 * 4、next()方法中利用反射创建接口实现类对象
 *
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
