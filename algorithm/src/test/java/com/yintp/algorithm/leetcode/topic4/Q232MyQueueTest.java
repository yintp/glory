package com.yintp.algorithm.leetcode.topic4;

import org.junit.Assert;
import org.junit.Test;

public class Q232MyQueueTest {

    @Test
    public void testQueue() {
        Q232MyQueue queue = new Q232MyQueue();
        queue.push(1);
        queue.push(2);
        Assert.assertEquals(1, queue.peek());
        Assert.assertEquals(1, queue.pop());
        Assert.assertFalse(queue.empty());
    }
}
