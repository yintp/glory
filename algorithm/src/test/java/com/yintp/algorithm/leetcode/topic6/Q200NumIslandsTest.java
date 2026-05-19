package com.yintp.algorithm.leetcode.topic6;

import org.junit.Assert;
import org.junit.Test;

public class Q200NumIslandsTest {

    private final Q200NumIslands solution = new Q200NumIslands();

    @Test
    public void testCase1() {
        char[][] grid = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        Assert.assertEquals(1, solution.numIslands(grid));
    }

    @Test
    public void testCase2() {
        char[][] grid = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        Assert.assertEquals(3, solution.numIslands(grid));
    }
}
