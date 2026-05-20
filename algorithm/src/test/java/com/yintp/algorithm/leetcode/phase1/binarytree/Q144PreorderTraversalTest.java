package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class Q144PreorderTraversalTest {
    private Q144PreorderTraversal sol = new Q144PreorderTraversal();

    private TreeNode buildTree() {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        return root;
    }

    @Test
    public void testExample() {
        assertEquals(Arrays.asList(1, 2, 3), sol.preorderTraversal(buildTree()));
    }

    @Test
    public void testEmpty() {
        assertTrue(sol.preorderTraversal(null).isEmpty());
    }
}
