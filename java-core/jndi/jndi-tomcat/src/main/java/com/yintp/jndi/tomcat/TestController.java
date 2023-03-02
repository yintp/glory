package com.yintp.jndi.tomcat;

import com.yintp.jndi.api.ExploitObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yintp
 */
@RestController
public class TestController {
    @RequestMapping("test")
    public Object test() {
        return new ExploitObject();
    }
}
