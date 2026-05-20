package com.yintp.algorithm.leetcode.phase1.stack;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q232StackToQueueTest {
    @Test
    public void testLeetCodeExample() {
        Q232StackToQueue queue = new Q232StackToQueue();
        queue.push(1); queue.push(2);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.pop());
        assertFalse(queue.empty());
    }

    @Test
    public void testFIFOOrder() {
        Q232StackToQueue queue = new Q232StackToQueue();
        queue.push(3); queue.push(1); queue.push(2);
        assertEquals(3, queue.pop());
        assertEquals(1, queue.pop());
        assertEquals(2, queue.pop());
        assertTrue(queue.empty());
    }
}
