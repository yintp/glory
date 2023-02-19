package com.yintp.agent.server.service;

/**
 * @author yintp
 */
public class BusinessService {
    public void doBusiness() {
        try {
            System.out.println("doBusiness...");
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
