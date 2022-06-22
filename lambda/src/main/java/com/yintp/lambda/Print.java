package com.yintp.lambda;

/**
 * @author yintp
 */
@FunctionalInterface
public interface Print<T> {
    void print(T t);
}
