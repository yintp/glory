package com.yintp.algorithm.leetcode.phase1.stack;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyStackTest {
    private MyStack stack;

    @Before
    public void setUp() { stack = new MyStack(); }

    @Test
    public void testPushAndPop() {
        stack.push(1); stack.push(2); stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    public void testPeek() {
        stack.push(5); stack.push(10);
        assertEquals(10, stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testAutoResize() {
        for (int i = 0; i < 20; i++) stack.push(i);
        assertEquals(20, stack.size());
        assertEquals(19, stack.pop());
    }
}
