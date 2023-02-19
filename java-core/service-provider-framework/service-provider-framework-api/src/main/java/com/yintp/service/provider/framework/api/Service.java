package com.yintp.service.provider.framework.api;

/**
 * Service Interface：
 * 服务接口，将服务通过抽象统一声明，以供客户端调用，由各个服务提供者提供具体实现。如JDBC的Connection接口。
 *
 * @author yintp
 */
public interface Service {
    String getName();
}
