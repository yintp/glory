package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class Q105BuildTreeTest {

    private final Q105BuildTree solution = new Q105BuildTree();

    @Test
    public void testCase1() {
        // preorder=[3,9,20,15,7], inorder=[9,3,15,20,7] → tree rooted at 3
        TreeNode root = solution.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        Assert.assertNotNull(root);
        Assert.assertEquals(3, root.val);
        Assert.assertEquals(9, root.left.val);
        Assert.assertEquals(20, root.right.val);
    }
}
