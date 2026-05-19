package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class Q235LowestCommonAncestorBSTTest {

    private final Q235LowestCommonAncestorBST solution = new Q235LowestCommonAncestorBST();

    @Test
    public void testCase1() {
        TreeNode p = new TreeNode(2);
        TreeNode q = new TreeNode(8);
        TreeNode root = new TreeNode(6,
            new TreeNode(2, new TreeNode(0), new TreeNode(4, new TreeNode(3), new TreeNode(5))),
            new TreeNode(8, new TreeNode(7), new TreeNode(9)));
        Assert.assertEquals(6, solution.lowestCommonAncestor(root, p, q).val);
    }
}
