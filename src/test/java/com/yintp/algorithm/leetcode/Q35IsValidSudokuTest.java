package com.yintp.algorithm.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yintp
 */
public class Q35IsValidSudokuTest {
    @Test
    public void testCase1() {
        Q35IsValidSudoku q35IsValidSudoku = new Q35IsValidSudoku();
        char[][] chars = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        boolean validSudoku = q35IsValidSudoku.isValidSudoku(chars);
        Assert.assertEquals(true, validSudoku);
    }

    @Test
    public void testCase2() {
        Q35IsValidSudoku q35IsValidSudoku = new Q35IsValidSudoku();
        char[][] chars = {
            {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        boolean validSudoku = q35IsValidSudoku.isValidSudoku(chars);
        Assert.assertEquals(false, validSudoku);
    }
}
