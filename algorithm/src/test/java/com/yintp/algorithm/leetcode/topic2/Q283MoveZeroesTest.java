package com.yintp.algorithm.leetcode.topic2;

import org.junit.Assert;
import org.junit.Test;

public class Q283MoveZeroesTest {

    private final Q283MoveZeroes solution = new Q283MoveZeroes();

    @Test
    public void testCase1() {
        int[] nums = {0, 1, 0, 3, 12};
        solution.moveZeroes(nums);
        Assert.assertArrayEquals(new int[]{1, 3, 12, 0, 0}, nums);
    }

    @Test
    public void testAllZeros() {
        int[] nums = {0, 0, 0};
        solution.moveZeroes(nums);
        Assert.assertArrayEquals(new int[]{0, 0, 0}, nums);
    }
}
