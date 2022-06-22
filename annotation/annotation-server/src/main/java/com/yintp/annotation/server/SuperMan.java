package com.yintp.annotation.server;

import com.yintp.annotation.processor.SayHello;

/**
 * @author yintp
 */
@Person(role = "son")
@Person(role = "brother")
@Person(role = "man")
@Person(role = "boyfriend")
@Person(role = "programmer")
@Person(role = "Chinese")
@SayHello
public class SuperMan {
}
