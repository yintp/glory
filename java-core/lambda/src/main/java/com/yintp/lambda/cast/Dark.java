package com.yintp.lambda.cast;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * @author yintp
 */
public class Dark {
    public static void say(Consumer<? super Integer> consumer) {
        /**
         * 因为实现类同时实现了两个接口
         * https://stackoverflow.com/questions/44288838/casting-java-functional-interfaces
         */
        if (consumer instanceof IntConsumer) {
            ((IntConsumer & Consumer) consumer).accept(0);
        } else {
            consumer.accept(1);
        }
    }
}
