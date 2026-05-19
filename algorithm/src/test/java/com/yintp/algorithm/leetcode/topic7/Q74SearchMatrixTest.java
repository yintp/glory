package com.yintp.algorithm.leetcode.topic7;

import org.junit.Assert;
import org.junit.Test;

public class Q74SearchMatrixTest {

    private final Q74SearchMatrix solution = new Q74SearchMatrix();

    @Test
    public void testFound() {
        int[][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        Assert.assertTrue(solution.searchMatrix(matrix, 3));
    }

    @Test
    public void testNotFound() {
        int[][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        Assert.assertFalse(solution.searchMatrix(matrix, 13));
    }
}
