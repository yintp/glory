package com.yintp.service.provider.framework.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provider Registration API：
 * 服务提供者注册API，用于注册服务提供者，使得客户端可以访问它实现的服务。如JDBC的DriverManager，当调用class.forName加载MySQL的驱动包时，就会把MySQL的Driver注册到DriverManager中，后续调用getConnection，返回的就是MySQL实现的Connection。
 * Service Access API：
 * 服务访问者接口，用于客户端获取相应的服务，很多时候该API都会与服务提供者注册API是一起的，如JDBC就是通过DriverManager的getConnection来获取具体的服务。
 *
 * @author yintp
 */
public class ServiceManager {
    private ServiceManager() {
    }

    private static final Map<String, ProviderInterface> providers = new ConcurrentHashMap<>();

    public static void registerProvider(String name, ProviderInterface provider) {
        providers.put(name, provider);
    }

    public static Service getService(String name) {
        ProviderInterface provider = providers.get(name);
        if (provider == null) {
            throw new IllegalArgumentException("No provider registered with name:" + name);
        }
        return provider.getService();
    }
}
