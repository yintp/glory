package com.yintp.algorithm.leetcode.topic9;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class Q46PermuteTest {

    private final Q46Permute solution = new Q46Permute();

    @Test
    public void testCase1() {
        List<List<Integer>> result = solution.permute(new int[]{1, 2, 3});
        Assert.assertEquals(6, result.size());
    }

    @Test
    public void testSingle() {
        List<List<Integer>> result = solution.permute(new int[]{1});
        Assert.assertEquals(1, result.size());
    }
}
