package com.yintp.lambda;

import com.yintp.lambda.cast.Dark;
import com.yintp.lambda.cast.OfInt;
import org.junit.Test;

import java.util.function.Consumer;

/**
 * @author yintp
 */
public class CastTest {
    @Test
    public void testLambdaCast() {
        OfInt c1 = System.out::println;
        Consumer c2 = System.out::println;
        Dark.say(c1);
        Dark.say(c2);
    }
}
