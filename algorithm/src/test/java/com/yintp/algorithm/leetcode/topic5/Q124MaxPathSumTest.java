package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class Q124MaxPathSumTest {

    private final Q124MaxPathSum solution = new Q124MaxPathSum();

    @Test
    public void testCase1() {
        TreeNode root = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        Assert.assertEquals(6, solution.maxPathSum(root));
    }

    @Test
    public void testCase2() {
        TreeNode root = new TreeNode(-10,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        Assert.assertEquals(42, solution.maxPathSum(root));
    }
}
