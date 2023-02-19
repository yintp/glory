package com.yintp.service.provider.framework.api;

/**
 * Service Provider API：
 * 服务提供者接口，用于创建服务实例，如JDBC中的Driver类，提供者要实现具体的获取实例的方法，该类被代替具体的服务Connection注册到DriverManager中，当调用DriverManager的getConnection方法时就会调用已注册的Driver的方法来创建实例。该组件是可选的，实际上可以直接将具体的Connection注册到DriverManager中，然后直接在getConnection方法中创建对应的Connection实例就可以了。
 *
 * @author yintp
 */
public interface ProviderInterface {
    Service getService();
}
