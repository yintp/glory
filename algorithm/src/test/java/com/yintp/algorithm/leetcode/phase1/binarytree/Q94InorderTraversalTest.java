package com.yintp.algorithm.leetcode.phase1.binarytree;

import com.yintp.algorithm.leetcode.structure.TreeNode;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class Q94InorderTraversalTest {
    private Q94InorderTraversal sol = new Q94InorderTraversal();

    private TreeNode buildTree() {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        return root;
    }

    @Test
    public void testExample() {
        assertEquals(Arrays.asList(1, 3, 2), sol.inorderTraversal(buildTree()));
    }

    @Test
    public void testEmpty() {
        assertTrue(sol.inorderTraversal(null).isEmpty());
    }
}
