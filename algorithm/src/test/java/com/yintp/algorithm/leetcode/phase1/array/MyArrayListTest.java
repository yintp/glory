package com.yintp.algorithm.leetcode.phase1.array;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyArrayListTest {
    private MyArrayList list;

    @Before
    public void setUp() {
        list = new MyArrayList();
    }

    @Test
    public void testAddAndGet() {
        list.add(1); list.add(2); list.add(3);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testAddAtIndex() {
        list.add(1); list.add(3);
        list.add(1, 2);
        assertEquals(3, list.size());
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testSet() {
        list.add(1); list.add(2);
        list.set(0, 99);
        assertEquals(99, list.get(0));
    }

    @Test
    public void testRemove() {
        list.add(1); list.add(2); list.add(3);
        assertEquals(2, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(3, list.get(1));
    }

    @Test
    public void testAutoResize() {
        for (int i = 0; i < 10; i++) list.add(i);
        assertEquals(10, list.size());
        assertEquals(9, list.get(9));
    }

    @Test
    public void testContains() {
        list.add(5);
        assertTrue(list.contains(5));
        assertFalse(list.contains(99));
    }
}
