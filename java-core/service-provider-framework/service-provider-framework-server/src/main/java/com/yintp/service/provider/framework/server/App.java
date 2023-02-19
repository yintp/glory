package com.yintp.service.provider.framework.server;

import com.yintp.service.provider.framework.api.Service;
import com.yintp.service.provider.framework.api.ServiceManager;

/**
 * 服务提供者框架：多个服务提供者实现一个服务，系统为客户端提供多个实现，并把他们从多个实现中解耦出来。
 * 服务提供者的改变对它们的客户端是透明的，这样提供了更好的可扩展性。
 *
 * @author yintp
 */
public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.yintp.service.provider.framework.impl.mysql.MysqlProviderImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Service service = ServiceManager.getService("mysqlService");
        String name = service.getName();
        System.out.println("name: " + name);
    }
}
