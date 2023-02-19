package com.yintp.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 定义一个远程接口，类需继承Remote接口，方法需抛出RemoteException异常
 * 用于标识其方法可以从非本地虚拟机上调用
 *
 * @author yintp
 */
public interface HelloService extends Remote {
    String say(User user) throws RemoteException;
}
