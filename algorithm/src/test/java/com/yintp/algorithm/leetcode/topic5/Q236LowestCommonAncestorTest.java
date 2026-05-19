package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class Q236LowestCommonAncestorTest {

    private final Q236LowestCommonAncestor solution = new Q236LowestCommonAncestor();

    @Test
    public void testCase1() {
        TreeNode p = new TreeNode(5);
        TreeNode q = new TreeNode(1);
        TreeNode root = new TreeNode(3, p, q);
        Assert.assertEquals(3, solution.lowestCommonAncestor(root, p, q).val);
    }
}
