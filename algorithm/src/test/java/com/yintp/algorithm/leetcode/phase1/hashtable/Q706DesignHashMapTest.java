package com.yintp.algorithm.leetcode.phase1.hashtable;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q706DesignHashMapTest {
    @Test
    public void testPutGetRemove() {
        Q706DesignHashMap map = new Q706DesignHashMap();
        map.put(1, 1); map.put(2, 2);
        assertEquals(1, map.get(1));
        assertEquals(-1, map.get(3));
        map.put(2, 1);
        assertEquals(1, map.get(2));
        map.remove(2);
        assertEquals(-1, map.get(2));
    }
}
