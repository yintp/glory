package com.yintp.algorithm.leetcode.topic9;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class Q51SolveNQueensTest {

    private final Q51SolveNQueens solution = new Q51SolveNQueens();

    @Test
    public void testN4() {
        List<List<String>> result = solution.solveNQueens(4);
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testN1() {
        List<List<String>> result = solution.solveNQueens(1);
        Assert.assertEquals(1, result.size());
    }
}
