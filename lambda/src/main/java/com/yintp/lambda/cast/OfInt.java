package com.yintp.lambda.cast;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * @author yintp
 */
public interface OfInt extends Consumer<Integer>, IntConsumer {

    @Override
    void accept(int value);

    @Override
    default void accept(Integer i) {
        accept(i.intValue());
    }
}
