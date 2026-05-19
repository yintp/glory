package com.yintp.algorithm.leetcode.topic9;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class Q78SubsetsTest {

    private final Q78Subsets solution = new Q78Subsets();

    @Test
    public void testCase1() {
        List<List<Integer>> result = solution.subsets(new int[]{1, 2, 3});
        Assert.assertEquals(8, result.size()); // 2^3
    }
}
