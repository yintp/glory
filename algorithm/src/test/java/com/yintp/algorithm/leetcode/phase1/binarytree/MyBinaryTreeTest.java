package com.yintp.algorithm.leetcode.phase1.binarytree;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class MyBinaryTreeTest {
    private MyBinaryTree bst;

    @Before
    public void setUp() {
        bst = new MyBinaryTree();
        // 插入顺序构造 BST: 4, 2, 6, 1, 3, 5, 7
        for (int v : new int[]{4, 2, 6, 1, 3, 5, 7}) bst.insert(v);
    }

    @Test
    public void testInorder() {
        // BST 中序遍历 = 升序
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), bst.inorder());
    }

    @Test
    public void testPreorder() {
        // 根 → 左 → 右
        assertEquals(Arrays.asList(4, 2, 1, 3, 6, 5, 7), bst.preorder());
    }

    @Test
    public void testPostorder() {
        // 左 → 右 → 根
        assertEquals(Arrays.asList(1, 3, 2, 5, 7, 6, 4), bst.postorder());
    }
}
