package com.yintp.jmx.server.agent;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 内置内存MBean demo
 *
 * @author yintp
 */
public class MemoryMonitor {
    public static void main(String[] args) throws InterruptedException {
        List<MemoryPoolMXBean> memoryBeans = ManagementFactory.getMemoryPoolMXBeans();
        while (true) {
            for (MemoryPoolMXBean mxBean : memoryBeans) {
                StringBuilder sb = new StringBuilder();
                MemoryUsage usage = mxBean.getUsage();
                sb.append("内存模型: [")
                    .append(mxBean.getType().name())
                    .append("], 内存空间名称: [")
                    .append(mxBean.getName())
                    .append("], 初始化[")
                    .append(bytesToMB(usage.getInit()))
                    .append("], 已使用[")
                    .append(bytesToMB(usage.getUsed()))
                    .append("], 可使用[")
                    .append(bytesToMB(usage.getCommitted()))
                    .append("], 最大[")
                    .append(bytesToMB(usage.getMax()))
                    .append("]");
                System.out.println(sb);
            }
            System.out.println("----------");
            TimeUnit.SECONDS.sleep(5);
        }
    }

    private static long bytesToMB(long init) {
        return init / 1024 / 1024;
    }
}
