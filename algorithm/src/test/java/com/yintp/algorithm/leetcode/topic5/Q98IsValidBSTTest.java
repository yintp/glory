package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class Q98IsValidBSTTest {

    private final Q98IsValidBST solution = new Q98IsValidBST();

    @Test
    public void testValidBST() {
        TreeNode root = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        Assert.assertTrue(solution.isValidBST(root));
    }

    @Test
    public void testInvalidBST() {
        TreeNode root = new TreeNode(5,
            new TreeNode(1),
            new TreeNode(4, new TreeNode(3), new TreeNode(6)));
        Assert.assertFalse(solution.isValidBST(root));
    }
}
