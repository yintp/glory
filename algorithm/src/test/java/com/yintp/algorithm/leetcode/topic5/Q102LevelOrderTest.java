package com.yintp.algorithm.leetcode.topic5;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class Q102LevelOrderTest {

    private final Q102LevelOrder solution = new Q102LevelOrder();

    @Test
    public void testCase1() {
        TreeNode root = new TreeNode(3,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        List<List<Integer>> result = solution.levelOrder(root);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(Arrays.asList(3), result.get(0));
        Assert.assertEquals(Arrays.asList(9, 20), result.get(1));
        Assert.assertEquals(Arrays.asList(15, 7), result.get(2));
    }
}
