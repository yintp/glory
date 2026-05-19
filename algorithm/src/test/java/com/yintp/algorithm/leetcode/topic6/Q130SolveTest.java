package com.yintp.algorithm.leetcode.topic6;

import org.junit.Assert;
import org.junit.Test;

public class Q130SolveTest {

    private final Q130Solve solution = new Q130Solve();

    @Test
    public void testCase1() {
        char[][] board = {
            {'X','X','X','X'},
            {'X','O','O','X'},
            {'X','X','O','X'},
            {'X','O','X','X'}
        };
        solution.solve(board);
        Assert.assertEquals('X', board[1][1]);
        Assert.assertEquals('X', board[1][2]);
        Assert.assertEquals('X', board[2][2]);
        Assert.assertEquals('O', board[3][1]); // border-connected, not captured
    }
}
