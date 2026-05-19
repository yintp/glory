package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class Q226InvertTreeTest {

    private final Q226InvertTree solution = new Q226InvertTree();

    @Test
    public void testCase1() {
        TreeNode root = new TreeNode(4,
            new TreeNode(2, new TreeNode(1), new TreeNode(3)),
            new TreeNode(7, new TreeNode(6), new TreeNode(9)));
        TreeNode result = solution.invertTree(root);
        Assert.assertEquals(4, result.val);
        Assert.assertEquals(7, result.left.val);
        Assert.assertEquals(2, result.right.val);
    }
}
