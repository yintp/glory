package com.yintp.algorithm.leetcode.phase1.queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q622DesignCircularQueueTest {
    @Test
    public void testLeetCodeExample() {
        Q622DesignCircularQueue cq = new Q622DesignCircularQueue(3);
        assertTrue(cq.enQueue(1)); assertTrue(cq.enQueue(2)); assertTrue(cq.enQueue(3));
        assertFalse(cq.enQueue(4));
        assertEquals(3, cq.Rear());
        assertTrue(cq.isFull());
        assertTrue(cq.deQueue());
        assertTrue(cq.enQueue(4));
        assertEquals(4, cq.Rear());
    }

    @Test
    public void testFrontRearOnEmpty() {
        Q622DesignCircularQueue cq = new Q622DesignCircularQueue(2);
        assertEquals(-1, cq.Front());
        assertEquals(-1, cq.Rear());
    }
}
