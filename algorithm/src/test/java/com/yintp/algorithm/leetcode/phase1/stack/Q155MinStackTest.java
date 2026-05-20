package com.yintp.algorithm.leetcode.phase1.stack;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q155MinStackTest {
    @Test
    public void testLeetCodeExample() {
        Q155MinStack minStack = new Q155MinStack();
        minStack.push(-2); minStack.push(0); minStack.push(-3);
        assertEquals(-3, minStack.getMin());
        minStack.pop();
        assertEquals(0, minStack.top());
        assertEquals(-2, minStack.getMin());
    }

    @Test
    public void testMinAfterPopMinElement() {
        Q155MinStack minStack = new Q155MinStack();
        minStack.push(5); minStack.push(3); minStack.push(7);
        assertEquals(3, minStack.getMin());
        minStack.pop();
        assertEquals(3, minStack.getMin());
        minStack.pop();
        assertEquals(5, minStack.getMin());
    }
}
