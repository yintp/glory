package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class Q104MaxDepthTest {

    private final Q104MaxDepth solution = new Q104MaxDepth();

    @Test
    public void testCase1() {
        // [3,9,20,null,null,15,7] → 3
        TreeNode root = new TreeNode(3,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        Assert.assertEquals(3, solution.maxDepth(root));
    }

    @Test
    public void testNull() {
        Assert.assertEquals(0, solution.maxDepth(null));
    }
}
