package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class Q101IsSymmetricTest {

    private final Q101IsSymmetric solution = new Q101IsSymmetric();

    @Test
    public void testSymmetric() {
        TreeNode root = new TreeNode(1,
            new TreeNode(2, new TreeNode(3), new TreeNode(4)),
            new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        Assert.assertTrue(solution.isSymmetric(root));
    }

    @Test
    public void testNotSymmetric() {
        TreeNode root = new TreeNode(1,
            new TreeNode(2, null, new TreeNode(3)),
            new TreeNode(2, null, new TreeNode(3)));
        Assert.assertFalse(solution.isSymmetric(root));
    }
}
