package com.yintp.algorithm.leetcode.topic4;

import org.junit.Assert;
import org.junit.Test;

public class Q155MinStackTest {

    @Test
    public void testMinStack() {
        Q155MinStack minStack = new Q155MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        Assert.assertEquals(-3, minStack.getMin());
        minStack.pop();
        Assert.assertEquals(0, minStack.top());
        Assert.assertEquals(-2, minStack.getMin());
    }
}
