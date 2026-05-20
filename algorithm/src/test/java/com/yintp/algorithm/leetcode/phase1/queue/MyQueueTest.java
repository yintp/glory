package com.yintp.algorithm.leetcode.phase1.queue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyQueueTest {
    private MyQueue queue;

    @Before
    public void setUp() { queue = new MyQueue(5); }

    @Test
    public void testOfferAndPoll() {
        queue.offer(1); queue.offer(2); queue.offer(3);
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    public void testPeek() {
        queue.offer(7);
        assertEquals(7, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    public void testFullAndEmpty() {
        assertTrue(queue.isEmpty());
        for (int i = 0; i < 5; i++) queue.offer(i);
        assertTrue(queue.isFull());
        assertFalse(queue.offer(99));
    }

    @Test
    public void testCircularReuse() {
        queue.offer(1); queue.offer(2); queue.offer(3);
        queue.poll(); queue.poll();
        queue.offer(4); queue.offer(5);
        assertEquals(3, queue.size());
        assertEquals(3, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(5, queue.poll());
    }
}
