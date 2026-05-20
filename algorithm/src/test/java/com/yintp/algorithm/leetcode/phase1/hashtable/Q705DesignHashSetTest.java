package com.yintp.algorithm.leetcode.phase1.hashtable;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q705DesignHashSetTest {
    @Test
    public void testAddContainsRemove() {
        Q705DesignHashSet set = new Q705DesignHashSet();
        set.add(1); set.add(2);
        assertTrue(set.contains(1));
        assertFalse(set.contains(3));
        set.add(2);
        assertTrue(set.contains(2));
        set.remove(2);
        assertFalse(set.contains(2));
    }
}
