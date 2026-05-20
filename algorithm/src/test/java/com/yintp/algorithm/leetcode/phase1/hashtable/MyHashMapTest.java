package com.yintp.algorithm.leetcode.phase1.hashtable;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyHashMapTest {
    private MyHashMap map;

    @Before
    public void setUp() { map = new MyHashMap(); }

    @Test
    public void testPutAndGet() {
        map.put(1, 100);
        map.put(2, 200);
        assertEquals(100, map.get(1));
        assertEquals(200, map.get(2));
    }

    @Test
    public void testUpdateExistingKey() {
        map.put(1, 100);
        map.put(1, 999);
        assertEquals(999, map.get(1));
    }

    @Test
    public void testGetMissing() {
        assertEquals(-1, map.get(999));
    }

    @Test
    public void testRemove() {
        map.put(1, 100);
        map.remove(1);
        assertEquals(-1, map.get(1));
    }

    @Test
    public void testContainsKey() {
        map.put(3, 30);
        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(99));
    }

    @Test
    public void testResizeOnManyEntries() {
        for (int i = 0; i < 20; i++) map.put(i, i * 10);
        for (int i = 0; i < 20; i++) assertEquals(i * 10, map.get(i));
    }
}
