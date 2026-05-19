package com.yintp.algorithm.leetcode.topic6;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class Q417PacificAtlanticTest {

    private final Q417PacificAtlantic solution = new Q417PacificAtlantic();

    @Test
    public void testCase1() {
        int[][] heights = {
            {1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}
        };
        List<List<Integer>> result = solution.pacificAtlantic(heights);
        Assert.assertEquals(7, result.size());
    }
}
