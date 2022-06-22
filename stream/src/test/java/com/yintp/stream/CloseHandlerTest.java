package com.yintp.stream;

import org.junit.Test;

/**
 * @author yintp
 */
public class CloseHandlerTest {
    @Test
    public void testAutoCallClose() {
        CloseHandler.autoCallClose();
    }

    @Test
    public void testCallClose() {
        CloseHandler.callClose();
    }
}
