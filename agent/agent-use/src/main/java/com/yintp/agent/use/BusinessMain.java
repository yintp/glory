package com.yintp.agent.use;

/**
 * 1、premain: -javaagent:D:\agent-api-1.0.jar
 * 2、agentmian: 使用attach机制
 *
 * @author yintp
 */
public class BusinessMain {
    public static void main(String[] args) {
        BusinessService service = new BusinessService();
        while (true) {
            service.doBusiness();
        }
    }
}
