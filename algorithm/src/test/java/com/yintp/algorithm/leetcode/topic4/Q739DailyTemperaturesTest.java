package com.yintp.algorithm.leetcode.topic4;

import org.junit.Assert;
import org.junit.Test;

public class Q739DailyTemperaturesTest {

    private final Q739DailyTemperatures solution = new Q739DailyTemperatures();

    @Test
    public void testCase1() {
        Assert.assertArrayEquals(
            new int[]{1, 1, 4, 2, 1, 1, 0, 0},
            solution.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})
        );
    }

    @Test
    public void testDescending() {
        Assert.assertArrayEquals(
            new int[]{0, 0, 0},
            solution.dailyTemperatures(new int[]{30, 20, 10})
        );
    }
}
