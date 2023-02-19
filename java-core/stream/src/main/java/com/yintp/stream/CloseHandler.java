package com.yintp.stream;

import java.util.stream.Stream;

/**
 * @author yintp
 */
public class CloseHandler {
    /**
     * try-with-resources
     */
    public static void autoCallClose() {
        try (Stream<String> stream = Stream.of("a", "b", "c")
            .onClose(() -> System.out.println("close handler1 do..."))
            .onClose(() -> System.out.println("close handler2 do..."))
        ) {
            stream.forEach(System.out::println);
        }
    }

    public static void callClose() {
        Stream<String> stream = Stream.of("a", "b", "c")
            .onClose(() -> System.out.println("close handler1 do..."))
            .onClose(() -> System.out.println("close handler2 do..."));
        stream.forEach(System.out::println);
        stream.close();
    }
}
