package com.yintp.algorithm.leetcode.phase1.array;

import org.junit.Test;
import static org.junit.Assert.*;

public class Q704BinarySearchTest {
    private Q704BinarySearch sol = new Q704BinarySearch();

    @Test
    public void testFound() {
        assertEquals(4, sol.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
    }

    @Test
    public void testNotFound() {
        assertEquals(-1, sol.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));
    }

    @Test
    public void testSingleElementFound() {
        assertEquals(0, sol.search(new int[]{5}, 5));
    }

    @Test
    public void testSingleElementNotFound() {
        assertEquals(-1, sol.search(new int[]{5}, 3));
    }
}
