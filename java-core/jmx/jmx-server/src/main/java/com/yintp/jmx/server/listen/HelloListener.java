package com.yintp.jmx.server.listen;

import com.yintp.jmx.server.mbean.Hello;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * @author yintp
 */
public class HelloListener implements NotificationListener {
    @Override
    public void handleNotification(Notification notification, Object handback) {
        if (handback instanceof Hello) {
            Hello hello = (Hello) handback;
            hello.sayHaha(notification.getMessage());
        }
    }
}

