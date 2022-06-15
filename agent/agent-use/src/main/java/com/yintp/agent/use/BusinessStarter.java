package com.yintp.agent.use;

/**
 * @author yintp
 */
public class BusinessStarter {
    public static void main(String[] args) {
        BusinessService service = new BusinessService();
        while (true) {
            service.doBusiness();
        }
    }
}
