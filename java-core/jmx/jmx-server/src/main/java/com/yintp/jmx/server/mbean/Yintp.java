package com.yintp.jmx.server.mbean;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 * Notification
 * MBean之间的通信是必不可少的，Notification就起到了在MBean之间沟通桥梁的作用。JMX 的通知由四部分组成：
 * 1、Notification这个相当于一个信息包，封装了需要传递的信息
 * 2、Notification broadcaster这个相当于一个广播器，把消息广播出。
 * 3、Notification listener 这是一个监听器，用于监听广播出来的通知信息。
 * 4、Notification filiter 这个一个过滤器，过滤掉不需要的通知，一般很少使用。
 *
 * @author yintp
 */
public class Yintp extends NotificationBroadcasterSupport implements YintpMBean {
    private int seq = 0;

    @Override
    public void hi() {
        // 创建一个信息包
        // 通知名称；谁发起的通知；序列号；发起通知时间；发送的消息
        Notification notify = new Notification("yintp.hi", this, ++seq, System.currentTimeMillis(), "im' yintp");
        sendNotification(notify);
    }
}
